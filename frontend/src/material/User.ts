import EMail from './EMail'

/**
 * Objects of this class represent users.
 */
abstract class User {
  /**
  * Email address of the user.
  */
  readonly email: EMail

  constructor(email: EMail) {
    this.email = email
  }

  /**
  * This function logs out the user. It's abstract for being implemented by the UserStore.
  */
  public abstract logout(): void
}

export default User
