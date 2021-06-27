
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
}

export default LoginCode
