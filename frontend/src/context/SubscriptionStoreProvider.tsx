import React, {createContext, useEffect, useState, FC} from 'react'
import SubscriptionStore from '../store/SubscriptionStore'
import useUserStore from '../hooks/UseUserStore'

export const SubscriptionStoreContext = createContext<SubscriptionStore | undefined>(undefined)

export const SubscriptionStoreProvider: FC = ({children}) => {
  const userStore = useUserStore()

  const [subscriptionStore] = useState(new SubscriptionStore())

  useEffect(() => {
    if (userStore) subscriptionStore.user = userStore.user
  }, [userStore, userStore?.user, subscriptionStore])

  return <SubscriptionStoreContext.Provider value={subscriptionStore}>{children}</SubscriptionStoreContext.Provider>
}
