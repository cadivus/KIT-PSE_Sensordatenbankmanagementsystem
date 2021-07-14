import Sensor from './Sensor'
import NotificationLevel from './NotificationLevel'
import User from './User'
import Id from './Id'

/**
 * Objects of this class represent a subscription.
 */
class Subscription {
  /**
   * Id of the subscription.
   */
  readonly id: Id

  /**
   * Sensor the subscription is for.
   */
  sensors: Array<Sensor>

  /**
   * Indicates whether the user gets a direct notification on failures.
   */
  directNotification: boolean

  /**
   * The notification level for status updates.
   */
  notificationLevel: NotificationLevel

  owner: User

  constructor(
    id: Id,
    sensors: Array<Sensor>,
    directNotification: boolean,
    notificationLevel: NotificationLevel,
    owner: User,
  ) {
    this.id = id
    this.sensors = sensors
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
