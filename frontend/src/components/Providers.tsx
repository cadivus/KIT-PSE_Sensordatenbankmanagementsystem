import React, {FC} from 'react'
import {UserStoreProvider} from '../context/UserStoreProvider'
import {SensorStoreProvider} from '../context/SensorStoreProvider'
import {ReplayStoreProvider} from '../context/ReplayStoreProvider'
import {SubscriptionStoreProvider} from '../context/SubscriptionStoreProvider'
import {AppContextProvider} from '../intl/AppContextProvider'
import Provider from '../context/LanguageProviderWrapper'

const Providers: FC = ({children}) => (
  <AppContextProvider>
    <Provider>
      <UserStoreProvider>
        <SensorStoreProvider>
          <ReplayStoreProvider>
            <SubscriptionStoreProvider>{children}</SubscriptionStoreProvider>
          </ReplayStoreProvider>
        </SensorStoreProvider>
      </UserStoreProvider>
    </Provider>
  </AppContextProvider>
)

export default Providers
