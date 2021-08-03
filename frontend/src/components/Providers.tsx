import React, {FC, Fragment} from 'react'
import {UserStoreProvider} from '../context/UserStoreProvider'
import {SensorStoreProvider} from '../context/SensorStoreProvider'
import {ReplayStoreProvider} from '../context/ReplayStoreProvider'
import {SubscriptionStoreProvider} from '../context/SubscriptionStoreProvider'
import LanguageProvider from '../context/LanguageProvider'

const Providers: FC = ({children}) => (
  <LanguageProvider>
    <UserStoreProvider>
      <SensorStoreProvider>
        <ReplayStoreProvider>
          <SubscriptionStoreProvider>{children}</SubscriptionStoreProvider>
        </ReplayStoreProvider>
      </SensorStoreProvider>
    </UserStoreProvider>
  </LanguageProvider>
)

export default Providers
