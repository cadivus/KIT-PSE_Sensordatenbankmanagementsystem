/**
 * Objects of this class represent the code for 2 factor authentification.
 */
class LoginCode {
  /**
   * The login code represented as a string.
   */
  readonly code: string

  constructor(code: string) {
    this.code = code
  }

  toString = (): string => {
    return this.code
  }
}

export default LoginCode
