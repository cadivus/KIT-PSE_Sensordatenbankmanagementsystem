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

    if (subscriptionStore && userStore) subscriptionStore.user = userStore.user
  }, [setSubscriptionStore, userStore, userStore?.user, subscriptionStore, sensorStore])

  return <SubscriptionStoreContext.Provider value={subscriptionStore}>{children}</SubscriptionStoreContext.Provider>
}
