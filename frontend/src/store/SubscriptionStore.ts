import User from '../material/User'
import Subscription from '../material/Subscription'
import SensorStore from './SensorStore'
import NotificationLevel from '../material/NotificationLevel'
import Id from '../material/Id'
import Sensor from '../material/Sensor'

/**
 * This is the storage for subscriptions.
 * It holds all the subscription objects, gets data from the backend and synchronizes data.
 */
class SubscriptionStore {
  private _user: User | null = null

  private readonly _sensorStore: SensorStore

  /**
   * Helds every subscription object
   */
  private subscriptions: Map<string, Subscription>

  constructor(sensorStore: SensorStore) {
    this._sensorStore = sensorStore
    this.subscriptions = new Map<string, Subscription>()
  }

  set user(user: User | null) {
    this._user = user
  }

  /**
   * Gets the subscriptions from the backend.
   */
  private getSubscriptionsFromBackend = (): void => {
    const {subscriptions, _sensorStore, _user} = this
    if (_user && (subscriptions.size === 0 || subscriptions.values().next().value.owner !== this._user)) {
      subscriptions.clear()
      for (let i = 1; i <= _sensorStore.sensors.length; i += 1) {
        const sensorList = _sensorStore.sensors.slice(0, i).reverse()
        const directNotification = i % 2 === 1
        const id = new Id(`${_user.email.email}-0-${i}`)
        const idStr = id.toString()
        subscriptions.set(idStr, new Subscription(id, sensorList, directNotification, new NotificationLevel(i), _user))
      }
    }
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
    sensors: Array<Sensor>,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
  ): Subscription | null => {
    const id = new Id(`id${new Date().getTime() / 1000}`)
    const {_user, subscriptions} = this
    if (!_user) return null
    const result = new Subscription(id, sensors, directNotification, notificationLevel, _user)
    subscriptions.set(id.toString(), result)
    return result
  }
}

export default SubscriptionStore
