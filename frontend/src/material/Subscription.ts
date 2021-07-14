import Sensor from './Sensor'
import NotificationLevel from './NotificationLevel'
import User from './User'

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

  owner: User

  constructor(sensor: Sensor, directNotification: boolean, notificationLevel: NotificationLevel, owner: User) {
    this.sensor = sensor
    this.directNotification = directNotification
    this.notificationLevel = notificationLevel
    this.owner = owner
  }

  /**
   * Adds an ActionListener for being triggered on changes.
   *
   * @param actionListener The ActionListener to be triggered.
   */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  onChange = (actionListener: any): void => {
    // TODO not implemented yet
  }
}

export default Subscription
