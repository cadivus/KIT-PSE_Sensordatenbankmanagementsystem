import EventEmitter from 'events'
import Thing from './Thing'
import NotificationLevel from './NotificationLevel'
import User from './User'
import Id from './Id'

declare interface Subscription {
  on(event: 'directNotification-change', listener: (name: string) => void): this
  on(event: 'notificationLevel-change', listener: (name: string) => void): this
}

/**
 * Objects of this class represent a subscription.
 */
abstract class Subscription extends EventEmitter {
  /**
   * Id of the subscription.
   */
  readonly id: Id

  /**
   * Thing the subscription is for.
   */
  // things: Array<Thing>

  thing: Thing

  /**
   * Indicates whether the user gets a direct notification on failures.
   */
  private _directNotification: boolean

  /**
   * The notification level for status updates.
   */
  private _notificationLevel: NotificationLevel

  owner: User

  constructor(id: Id, thing: Thing, directNotification: boolean, notificationLevel: NotificationLevel, owner: User) {
    super()
    this.id = id
    this.thing = thing
    this._directNotification = directNotification
    this._notificationLevel = notificationLevel
    this.owner = owner
  }

  set directNotification(directNotification: boolean) {
    this._directNotification = directNotification
    this.emit('directNotification-change')
  }

  get directNotification(): boolean {
    const {_directNotification} = this
    return _directNotification
  }

  set notificationLevel(notificationLevel: NotificationLevel) {
    this._notificationLevel = notificationLevel
    this.emit('notificationLevel-change')
  }

  get notificationLevel(): NotificationLevel {
    const {_notificationLevel} = this
    return _notificationLevel
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

  /**
   * This function unsubscibes the subscription.
   *
   * @return True on success
   */
  public abstract unsubscribe(): Promise<boolean>
}

export default Subscription
