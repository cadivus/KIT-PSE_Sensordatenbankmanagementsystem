import User from '../material/User'
import Subscription from '../material/Subscription'
import ThingStore from './ThingStore'
import NotificationLevel from '../material/NotificationLevel'
import Id from '../material/Id'
import Thing from '../material/Thing'
import {getJson, getText, postJsonGetJson, postJsonGetText} from './communication/restClient'
import {CONFIRMCODE_PATH, GET_SUBSCRIPTION_PATH, POST_SUBSCRIPTION_PATH} from './communication/notificationUrlCreator'
import LoginCode from '../material/LoginCode'

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
          const directNotification = element.alert
          const id = new Id(`${_user?.email.toString()}${element.sensorID}${element.notificationLevel}`)
          const idStr = id.toString()
          const thing = _thingStore.getThing(element.thingID)
          if (thing) {
            // @ts-ignore
            const subs = new (class extends Subscription {
              unsubscribe(): boolean {
                return unsubscribeById(id)
              }
            })(thing, directNotification, element.notificationLevel, _user)
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

  createSubscription = (
    things: Array<Thing>,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
  ): Subscription | null => {
    const {unsubscribe: unsubscribeById} = this

    const id = new Id(`id${new Date().getTime() / 1000}`)
    const {_user, subscriptions} = this

    if (!_user) return null

    const result = new (class extends Subscription {
      unsubscribe(): boolean {
        return unsubscribeById(id)
      }
    })(id, things, directNotification, notificationLevel, _user)
    subscriptions.set(id.toString(), result)
    things.forEach(thing => {
      const subscriptionSetting = {
        mailAddress: _user.email.toString(),
        sensorID: thing.id.toString(),
        reportInterval: notificationLevel.days,
      }
      console.log(subscriptionSetting)
      postJsonGetText(POST_SUBSCRIPTION_PATH, subscriptionSetting)
      getText(
        this.getSubscriptionPath(
          subscriptionSetting.mailAddress,
          subscriptionSetting.sensorID,
          subscriptionSetting.reportInterval,
        ),
      )
    })
    return result
  }

  private unsubscribe = (id: Id): boolean => {
    const {subscriptions} = this
    if (!subscriptions.has(id.toString())) return false

    return subscriptions.delete(id.toString())
  }

  getSubscriptionPath = (mailAddress: string, thingID: string, notificationLevel: number): string => {
    const path = `${POST_SUBSCRIPTION_PATH}?mailAddress=${mailAddress}&sensorID=${thingID}&reportInterval=${notificationLevel}`
    console.log(path)
    return path
  }
}

export default SubscriptionStore
