import EventEmitter from 'events'
import User from '../types/User'
import EMail from '../types/EMail'
import LoginCode from '../types/LoginCode'
import {getText, postJsonGetText} from './communication/restClient'
import {
  getConfirmCodeUrl,
  getLoginUrl,
  LOGIN_STATE_PATH,
  LOGIN_SUCCESS_RESPONSE,
} from './communication/notificationUrlCreator'

declare interface UserStore {
  on(event: 'login-change', listener: (name: string) => void): this
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

  code: LoginCode | undefined

  constructor() {
    super()

    const {implementUser} = this
    getText(LOGIN_STATE_PATH).then(loginState => {
      if (loginState === 'false' || loginState === '') return

      const email = new EMail(loginState)

      this.user = implementUser(email)
      this.emit('login-change')
    })
  }

  /**
   * Requests sending a login code to the specified email address.
   *
   * @param email Email address to send the code to
   * @return True on success, false otherwise
   */
  requestStep1 = (email: EMail): Promise<void> => {
    const resultPromise = new Promise<void>((resolve, reject) => {
      const path = getConfirmCodeUrl(email)
      getText(path).then(loginCode => {
        // eslint-disable-next-line no-console
        console.log(loginCode)
        this.code = new LoginCode(loginCode)
        resolve()
      })
    })

    return resultPromise
  }

  /**
   * Logs in a user after getting the login code.
   *
   * @param email Email address of the user
   * @param loginCode Login code of the user
   * @return The user object on success, null otherwise
   */
  requestUser = (email: EMail, loginCode: LoginCode): Promise<User | null> => {
    const {implementUser} = this

    const logoutUser = () => {
      this.user = null
      this.emit('login-change')
    }

    const path = getLoginUrl(email, loginCode)

    const resultPromise = new Promise<User | null>((resolve, reject) => {
      getText(path).then(res => {
        if (res === LOGIN_SUCCESS_RESPONSE) {
          this.user = implementUser(email)
          this.emit('login-change')
          resolve(this.user)
        } else {
          this.user = null
          resolve(null)
        }
      })
    })

    return resultPromise
  }

  implementUser = (email: EMail): User => {
    const logoutUser = () => {
      document.cookie = `${email.toString().replace(/[^\w\s]/gi, '')}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`
      this.user = null
      this.emit('login-change')
    }

    const newUser = new (class extends User {
      logout(): void {
        logoutUser()
      }
    })(email)

    return newUser
  }
}

export default UserStore
