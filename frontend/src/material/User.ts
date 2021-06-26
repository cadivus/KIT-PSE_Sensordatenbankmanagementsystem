/**
 * Objects of this class represent users.
 */
import EMail from './EMail'

abstract class User {
    /**
     * Email address of the user.
     */
    readonly email: EMail
    
    /**
     * This function logs out the user. It's abstract for being implemented by the UserStore.
     */
    public abstract logout(): void
}

export default User
