import React, {createContext, FC, useState} from 'react'
import DatastreamStore from '../store/DatastreamStore'

export const DatastreamStoreContext = createContext<DatastreamStore | undefined>(undefined)

export const DatastreamStoreProvider: FC = ({children}) => {
  const [datastreamStore] = useState(new DatastreamStore())

  return <DatastreamStoreContext.Provider value={datastreamStore}>{children}</DatastreamStoreContext.Provider>
}
