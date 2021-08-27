import User from '../material/User'
import Subscription from '../material/Subscription'
import ThingStore from './ThingStore'
import NotificationLevel from '../material/NotificationLevel'
import Id from '../material/Id'
import Thing from '../material/Thing'
import {getJson, postJsonAsURLGetText} from './communication/restClient'
import {
  GET_SUBSCRIPTION_PATH,
  POST_SUBSCRIPTION_PATH,
  POST_UBSUBSCRIBE_PATH,
} from './communication/notificationUrlCreator'

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
  private getSubscriptionsFromBackend = (): void => {
    const {subscriptions, _thingStore, _user, unsubscribe: unsubscribeById} = this
    if (_user) {
      const path = `${GET_SUBSCRIPTION_PATH}/${_user?.email.toString()}`
      getJson(path).then(subscriptionJSON => {
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        subscriptionJSON.forEach((element: any) => {
          const directNotification = element.toggleAlert
          const notifcationLevel = new NotificationLevel(element.reportInterval)
          const id = new Id(`${element.sensorId}`)
          const idStr = id.toString()
          const thing = _thingStore.getThing(id)
          if (thing) {
            const subs = new (class extends Subscription {
              unsubscribe(): boolean {
                return unsubscribeById(id)
              }
            })(id, thing, directNotification, notifcationLevel, _user)
            subscriptions.set(idStr, subs)
          }
        })
      })
    }
    /*
    if (_user && (subscriptions.size === 0 || subscriptions.values().next().value.owner !== this._user)) {
      subscriptions.clear()
      for (let i = 1; i <= _thingStore.things.length; i += 1) {
        const thingList = _thingStore.things.slice(0, i).reverse()
        const directNotification = i % 2 === 1
        const id = new Id(`${_user.email.email}-0-${i}`)
        const idStr = id.toString()
        const subs = new (class extends Subscription {
          unsubscribe(): boolean {
            return unsubscribeById(id)
          }
        })(id, thingList, directNotification, new NotificationLevel(i), _user)
        subscriptions.set(idStr, subs)
      }
    }
     */
  }

  /**
   * Gets all subscriptions owned by user.
   *
   * @return List with the subscriptions.
   */
  getSubscriptions = (): Array<Subscription> => {
    const {getSubscriptionsFromBackend} = this

    if (!this._user) return []

    getSubscriptionsFromBackend()
    const {subscriptions} = this
    const result = new Array<Subscription>()

    subscriptions.forEach(e => {
      result.push(e)
    })

    return result
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
    const {unsubscribe: unsubscribeById} = this

    const id = new Id(thing.id.toString())
    const {_user, subscriptions} = this

    if (!_user) return null

    const result = new (class extends Subscription {
      unsubscribe(): boolean {
        return unsubscribeById(id)
      }
    })(id, thing, directNotification, notificationLevel, _user)
    subscriptions.set(id.toString(), result)
    const setting = {
      mailAddress: _user.email.toString(),
      sensorID: thing.id.toString(),
      reportInterval: notificationLevel.days,
      directNotification: directNotification.valueOf(),
    }
    postJsonAsURLGetText(
      this.getSubscriptionPath(
        setting.mailAddress,
        setting.sensorID,
        setting.reportInterval,
        setting.directNotification,
      ),
    )
    return result
  }

  private unsubscribe = (id: Id): boolean => {
    const {subscriptions} = this
    if (!subscriptions.has(id.toString())) return false
    if (!this._user) return false
    const path = this.getUnsubscriptionPath(this._user?.email.toString(), id.toString())
    postJsonAsURLGetText(path)
    return subscriptions.delete(id.toString())
  }

  getUnsubscriptionPath = (mailAddress: string, thingID: string): string => {
    return `${POST_UBSUBSCRIBE_PATH}?mailAddress=${mailAddress}&sensorID=${thingID}`
  }

  getSubscriptionPath = (
    mailAddress: string,
    thingID: string,
    notificationLevel: number,
    directNotification: boolean,
  ): string => {
    return `${POST_SUBSCRIPTION_PATH}?mailAddress=${mailAddress}&sensorID=${thingID}&reportInterval=${notificationLevel}&toggleAlert=${directNotification}`
  }
}

export default SubscriptionStore
