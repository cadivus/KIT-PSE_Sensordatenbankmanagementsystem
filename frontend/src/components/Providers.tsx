import React, {FC, Fragment} from 'react'
import {UserStoreProvider} from '../context/UserStoreProvider'
import {SensorStoreProvider} from '../context/SensorStoreProvider'
import {ReplayStoreProvider} from '../context/ReplayStoreProvider'
import {SubscriptionStoreProvider} from '../context/SubscriptionStoreProvider'
import Provider from '../i18n/Provider'

const Providers: FC = ({children}) => (
  <Provider>
    <UserStoreProvider>
      <SensorStoreProvider>
        <ReplayStoreProvider>
          <SubscriptionStoreProvider>{children}</SubscriptionStoreProvider>
        </ReplayStoreProvider>
      </SensorStoreProvider>
    </UserStoreProvider>
  </Provider>
)

export default Providers
