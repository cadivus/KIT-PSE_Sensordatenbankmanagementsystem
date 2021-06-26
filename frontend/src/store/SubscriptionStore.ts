/**
 * This is the storage for subscriptions.
 * It holds all the subscription objects, gets data from the backend and synchronizes data.
 */
import User from '../material/User'
import Subscription from '../material/Subscription'

class SubscriptionStore {
    /**
     * Helds every subscription object
     */
    private subscriptions: Array<Subscription>
    
    /**
     * Gets the subscriptions from the backend.
     * 
     * @param user Owner of the subscriptions.
     */
    private getSubscriptionsFromBackend(user: User): void {
        
    }
    
    /**
     * Gets all subscriptions owned by user.
     * 
     * @param user Owner of the subscriptions.
     * @return List with the subscriptions.
     */
    getSubscriptions = (user: User): Array<Subscription> => {
        return null
    }
}

export default SubscriptionStore
