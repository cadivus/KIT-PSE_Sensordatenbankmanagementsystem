import User from '../material/User'
import Subscription from '../material/Subscription'
import SensorStore from './SensorStore'
import NotificationLevel from '../material/NotificationLevel'

/**
 * This is the storage for subscriptions.
 * It holds all the subscription objects, gets data from the backend and synchronizes data.
 */
class SubscriptionStore {
  private _user: User | null = null

  private readonly _sensorStore: SensorStore

  constructor(sensorStore: SensorStore) {
    this._sensorStore = sensorStore
  }

  set user(user: User | null) {
    this._user = user
    console.log('SubscriptionStore setUser', user)
  }

  /**
   * Helds every subscription object
   */
  private subscriptions: Array<Subscription> = []

  /**
   * Gets the subscriptions from the backend.
   */
  private getSubscriptionsFromBackend = (): void => {
    const {subscriptions, _sensorStore, _user} = this
    if (_user && (subscriptions.length === 0 || subscriptions[0].owner !== this._user)) {
      subscriptions.length = 0
      for (let i = 0; i < _sensorStore.sensors.length; i += 1) {
        subscriptions.push(new Subscription(_sensorStore.sensors[i], true, new NotificationLevel(i), _user))
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
    return subscriptions
  }
}

export default SubscriptionStore
