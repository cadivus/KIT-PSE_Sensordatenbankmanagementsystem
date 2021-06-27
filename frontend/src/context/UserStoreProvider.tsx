import React, {createContext, useState, FC} from 'react'
import UserStore from '../store/UserStore'

export const UserStoreContext = createContext<UserStore | undefined>(undefined)

export const UserStoreProvider: FC = ({children}) => {
    const [userStore] = useState(new UserStore())

    return <UserStoreContext.Provider value={userStore}>{children}</UserStoreContext.Provider>
}
