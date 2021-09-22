/**
 * This is a representation for email addresses.
 */
class EMail {
  /**
   * Email address stored as a string.
   */
  readonly email: string

  constructor(email: string) {
    this.email = email
  }

  toString = (): string => {
    return this.email
  }
}

export default EMail
