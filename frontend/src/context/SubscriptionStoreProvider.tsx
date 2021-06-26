import React, {createContext, useEffect, useState, FC} from 'react'

export const SubscriptionStoreContext = createContext('SubscriptionStore')

export const SubscriptionStoreProvider: FC = ({children}) => {
    return <SubscriptionStoreContext.Provider value={"SubscriptionStore"}>{children}</SubscriptionStoreContext.Provider>
}
