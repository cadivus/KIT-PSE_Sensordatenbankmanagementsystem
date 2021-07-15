/**
 * This is a representation for log levels.
 */
class NotificationLevel {
  /**
   * Log level represented as an integer.
   */
  readonly days: number

  /**
   * Indicates whether logging is active
   */
  readonly active: boolean

  constructor(days: number, active = true) {
    this.days = days
    this.active = active
  }
}

export default NotificationLevel
