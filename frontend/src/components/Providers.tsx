import React, {FC} from 'react'
import {UserStoreProvider} from '../context/UserStoreProvider'
import {ThingStoreProvider} from '../context/ThingStoreProvider'
import {ReplayStoreProvider} from '../context/ReplayStoreProvider'
import {SubscriptionStoreProvider} from '../context/SubscriptionStoreProvider'
import {AppContextProvider} from '../intl/AppContextProvider'
import {DatastreamStoreProvider} from '../context/DatastreamStoreProvider'
import Provider from '../context/LanguageProviderWrapper'

const Providers: FC = ({children}) => (
  <AppContextProvider>
    <Provider>
      <UserStoreProvider>
        <ThingStoreProvider>
          <DatastreamStoreProvider>
            <ReplayStoreProvider>
              <SubscriptionStoreProvider>{children}</SubscriptionStoreProvider>
            </ReplayStoreProvider>
          </DatastreamStoreProvider>
        </ThingStoreProvider>
      </UserStoreProvider>
    </Provider>
  </AppContextProvider>
)

export default Providers
