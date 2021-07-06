import User from '../material/User'
import Subscription from '../material/Subscription'

/**
 * This is the storage for subscriptions.
 * It holds all the subscription objects, gets data from the backend and synchronizes data.
 */
class SubscriptionStore {
  private _user: User | null = null

  set user(user: User | null) {
    this._user = user
  }

  /**
  * Helds every subscription object
  */
  private subscriptions: Array<Subscription> = []

  /**
  * Gets the subscriptions from the backend.
  */
  private getSubscriptionsFromBackend(): void {

  }

  /**
  * Gets all subscriptions owned by user.
  *
  * @return List with the subscriptions.
  */
  getSubscriptions = (): Array<Subscription> => {
    const {_user, getSubscriptionsFromBackend} = this
    if (!_user) return []

    getSubscriptionsFromBackend()
    const {subscriptions} = this
    return subscriptions
  }
}

export default SubscriptionStore