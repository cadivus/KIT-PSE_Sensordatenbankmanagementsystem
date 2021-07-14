import React, {createContext, useEffect, useState, FC} from 'react'
import SubscriptionStore from '../store/SubscriptionStore'
import useUserStore from '../hooks/UseUserStore'
import useSensorStore from '../hooks/UseSensorStore'

export const SubscriptionStoreContext = createContext<SubscriptionStore | undefined>(undefined)

export const SubscriptionStoreProvider: FC = ({children}) => {
  const userStore = useUserStore()
  const sensorStore = useSensorStore()

  const [subscriptionStore, setSubscriptionStore] = useState<SubscriptionStore | undefined>(undefined)

  useEffect(() => {
    if (sensorStore && !subscriptionStore) setSubscriptionStore(new SubscriptionStore(sensorStore))
  }, [setSubscriptionStore, userStore, subscriptionStore, sensorStore])

  useEffect(() => {
    if (subscriptionStore && userStore) {
      userStore.on('login-change', () => {
        subscriptionStore.user = userStore.user
      })
    }
  }, [userStore, subscriptionStore])

  return <SubscriptionStoreContext.Provider value={subscriptionStore}>{children}</SubscriptionStoreContext.Provider>
}
