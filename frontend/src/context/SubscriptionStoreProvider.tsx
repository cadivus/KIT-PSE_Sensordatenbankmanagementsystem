import React, {createContext, useEffect, useState, FC} from 'react'
import SubscriptionStore from '../store/SubscriptionStore'
import useUserStore from '../hooks/UseUserStore'
import useThingStore from '../hooks/UseThingStore'

export const SubscriptionStoreContext = createContext<SubscriptionStore | undefined>(undefined)

export const SubscriptionStoreProvider: FC = ({children}) => {
  const userStore = useUserStore()
  const thingStore = useThingStore()

  const [subscriptionStore, setSubscriptionStore] = useState<SubscriptionStore | undefined>(undefined)

  useEffect(() => {
    if (thingStore && !subscriptionStore) setSubscriptionStore(new SubscriptionStore(thingStore))
  }, [setSubscriptionStore, userStore, subscriptionStore, thingStore])

  useEffect(() => {
    if (subscriptionStore && userStore) {
      userStore.on('login-change', () => {
        subscriptionStore.user = userStore.user
      })
    }
  }, [userStore, subscriptionStore])

  return <SubscriptionStoreContext.Provider value={subscriptionStore}>{children}</SubscriptionStoreContext.Provider>
}
