import React, {FC} from 'react'
import {UserStoreProvider} from '../context/UserStoreProvider'
import {SensorStoreProvider} from '../context/SensorStoreProvider'
import {ReplayStoreProvider} from '../context/ReplayStoreProvider'
import {SubscriptionStoreProvider} from '../context/SubscriptionStoreProvider'

const Providers: FC = ({children}) => (
  <UserStoreProvider>
    <SensorStoreProvider>
      <ReplayStoreProvider>
        <SubscriptionStoreProvider>{children}</SubscriptionStoreProvider>
      </ReplayStoreProvider>
    </SensorStoreProvider>
  </UserStoreProvider>
)

export default Providers
