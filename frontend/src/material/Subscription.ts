import Sensor from './Sensor'
import NotificationLevel from './NotificationLevel'

/**
 * Objects of this class represent a subscription.
 */
class Subscription {
    /**
     * Sensor the subscription is for.
     */
    sensor: Sensor
    
    /**
     * Indicates whether the user gets a direct notification on failures.
     */
    directNotification: boolean
    
    /**
     * The notification level for status updates.
     */
    notificationLevel: NotificationLevel

    constructor(sensor: Sensor, directNotification: boolean, notificationLevel: NotificationLevel) {
        this.sensor = sensor
        this.directNotification = directNotification
        this.notificationLevel = notificationLevel
    }
    
    /**
     * Adds an ActionListener for being triggered on changes.
     * 
     * @param actionListener The ActionListener to be triggered.
     */
    onChange = (actionListener: any): void => {
        
    }
}

export default Subscription
