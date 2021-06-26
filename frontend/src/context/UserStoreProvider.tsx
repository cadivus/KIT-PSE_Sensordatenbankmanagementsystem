import React, {createContext, useEffect, useState, FC} from 'react'

export const UserStoreContext = createContext('UserStore')

export const UserStoreProvider: FC = ({children}) => {
    return <UserStoreContext.Provider value={"UserStore"}>{children}</UserStoreContext.Provider>
}
