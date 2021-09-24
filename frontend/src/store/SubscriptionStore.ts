import User from '../types/User'
import Subscription from '../types/Subscription'
import ThingStore from './ThingStore'
import NotificationLevel from '../types/NotificationLevel'
import Id from '../types/Id'
import Thing from '../types/Thing'
import {getJson, postJsonGetText} from './communication/restClient'
import {getPostSubscriptionPath, getSubscriptionsUrl, getUnsubscribePath} from './communication/notificationUrlCreator'

/**
 * This is the storage for subscriptions.
 * It holds all the subscription objects, gets data from the backend and synchronizes data.
 */
class SubscriptionStore {
  private _user: User | null = null

  private readonly _thingStore: ThingStore

  /**
   * Helds every subscription object
   */
  private subscriptions: Map<string, Subscription>

  constructor(thingStore: ThingStore) {
    this._thingStore = thingStore
    this.subscriptions = new Map<string, Subscription>()
  }

  set user(user: User | null) {
    this._user = user
  }

  /**
   * Gets the subscriptions from the backend.
   */
  private getSubscriptionsFromBackend = (): Promise<void> => {
    const {subscriptions, _thingStore, _user, unsubscribe: unsubscribeById, implementSubscription} = this

    const resultPromise = new Promise<void>((resolve, reject) => {
      if (_user) {
        const {email} = _user
        const path = getSubscriptionsUrl(email)
        getJson(path).then(async subscriptionJSON => {
          // eslint-disable-next-line
          for (const element of subscriptionJSON) {
            const directNotification = element.toggleAlert
            const notifcationLevel = new NotificationLevel(element.reportInterval)
            const id = new Id(`${element.sensorId}`)
            const idStr = id.toString()
            // eslint-disable-next-line no-await-in-loop
            const thing = await _thingStore.getThing(id)
            const subs = implementSubscription(id, thing, directNotification, notifcationLevel, _user)
            subscriptions.set(idStr, subs)
          }
          resolve()
        })
      }
    })

    return resultPromise
  }

  /**
   * Gets all subscriptions owned by user.
   *
   * @return List with the subscriptions.
   */
  getSubscriptions = (): Promise<Array<Subscription>> => {
    const {getSubscriptionsFromBackend} = this

    const resultPromise = new Promise<void>((resolve, reject) => {
      if (!this._user) {
        resolve([])
        return
      }

      getSubscriptionsFromBackend().then(() => {
        const {subscriptions} = this
        const result = new Array<Subscription>()

        subscriptions.forEach(e => {
          result.push(e)
        })

        resolve(result)
      })
    })

    return resultPromise
  }

  getSubscription = (id: Id): Subscription | undefined => {
    const {getSubscriptionsFromBackend} = this
    getSubscriptionsFromBackend()
    const {subscriptions} = this
    return subscriptions.get(id.toString())
  }

  createSubscriptions = (
    things: Array<Thing>,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
  ): void => {
    things.forEach((element: Thing) => {
      this.createSubscription(element, directNotification, notificationLevel)
    })
  }

  createSubscription = (
    thing: Thing,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
  ): Subscription | null => {
    const {unsubscribe: unsubscribeById, implementSubscription} = this

    const id = new Id(thing.id.toString())
    const {_user, subscriptions} = this

    if (!_user) return null

    const result = implementSubscription(id, thing, directNotification, notificationLevel, _user)
    subscriptions.set(id.toString(), result)
    const setting = {
      mailAddress: _user.email,
      sensorID: thing.id,
      reportInterval: notificationLevel.days,
      directNotification: directNotification.valueOf(),
    }
    postJsonGetText(
      getPostSubscriptionPath(
        setting.mailAddress,
        setting.sensorID,
        setting.reportInterval,
        setting.directNotification,
      ),
      {},
    )
    return result
  }

  private unsubscribe = (id: Id): Promise<boolean> => {
    // eslint-disable-next-line
    const resultPromise = new Promise<boolean>(async (resolve, reject) => {
      const {subscriptions} = this
      if (!subscriptions.has(id.toString())) return false
      if (!this._user) return false
      const path = getUnsubscribePath(this._user.email, id)
      await postJsonGetText(path, {})
      resolve(subscriptions.delete(id.toString()))
    })

    return resultPromise
  }

  private implementSubscription = (
    id: Id,
    thing: Thing,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
    owner: User,
  ): Subscription => {
    const {unsubscribe: unsubscribeById} = this

    const subscription = new (class extends Subscription {
      unsubscribe(): boolean {
        return unsubscribeById(id)
      }
    })(id, thing, directNotification, notificationLevel, owner)

    return subscription
  }
}

export default SubscriptionStore
