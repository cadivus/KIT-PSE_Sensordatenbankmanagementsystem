/**
 * This is the storage for users.
 * It holds all the user objects, gets data from the backend and synchronizes data.
 */
import User from '../material/User'
import EMail from '../material/EMail'
import LoginCode from '../material/LoginCode'

class UserStore {
  /**
  * The user currently logged in.
  * It will be write protected for store users.
  */
  user: User | null = null

  /**
  * Requests sending a login code to the specified email address.
  *
  * @param email Email address to send the code to
  * @return True on success, false otherwise
  */
  requestStep1 = (email: EMail): boolean => {
    return true
  }

  /**
  * Logs in a user after getting the login code.
  *
  * @param email Email address of the user
  * @param loginCode Login code of the user
  * @return The user object on success, null otherwise
  */
  requestUser = (email: EMail, loginCode: LoginCode): User | null => {
    const logoutUser = () => {
      this.user = null
    }

    this.user = new class extends User {
      logout(): void {
        logoutUser()
      }
    }(email)

    const {user} = this
    return user
  }
}

export default UserStore
