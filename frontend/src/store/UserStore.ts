import EventEmitter from 'events'
import User from '../material/User'
import EMail from '../material/EMail'
import LoginCode from '../material/LoginCode'
import {getJson} from './communication/restClient'
import {NOTIFICATION_PATH} from './communication/notificationUrlCreator'

declare interface UserStore {
  on(event: 'login-change', listener: (name: string) => void): this
}

class useableUser extends User {
  public logout(): void {
    throw new Error('Method not implemented.')
  }

  loginCode: LoginCode | undefined

  set setLoginCode(value: (loginCode: LoginCode) => void) {
    this.setLoginCode = value
  }

  get setLoginCode(): (loginCode: LoginCode) => void {
    return this.setLoginCode
  }

  // eslint-disable-next-line no-useless-constructor
  constructor(email: EMail) {
    super(email)
  }
}

/**
 * This is the storage for users.
 * It holds all the user objects, gets data from the backend and synchronizes data.
 */
class UserStore extends EventEmitter {
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
    const path = `${NOTIFICATION_PATH}/getConfirmationCode/{${email}}`
    getJson(path).then(loginCodeJSON => {
      console.log(loginCodeJSON)
      const code = loginCodeJSON
      // eslint-disable-next-line new-cap
      const currentUser = new useableUser(email)
      currentUser.setLoginCode(code)
    })
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
      this.emit('login-change')
    }

    this.user = new (class extends User {
      logout(): void {
        logoutUser()
      }
    })(email)

    const {user} = this
    this.emit('login-change')
    return user
  }
}

export default UserStore
