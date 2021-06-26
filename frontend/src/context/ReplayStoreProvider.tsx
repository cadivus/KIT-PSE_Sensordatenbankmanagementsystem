import React, {FC, createContext, useEffect, useState} from 'react'

export const ReplayStoreContext = createContext('ReplayStore')

export const ReplayStoreProvider: FC = ({children}) => {
    return <ReplayStoreContext.Provider value={"ReplayStore"}>{children}</ReplayStoreContext.Provider>
}
