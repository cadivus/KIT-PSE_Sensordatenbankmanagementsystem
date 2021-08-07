import React, {createContext, FC, useState} from 'react'
import ThingStore from '../store/ThingStore'

export const ThingStoreContext = createContext<ThingStore | undefined>(undefined)

export const ThingStoreProvider: FC = ({children}) => {
  const [thingStore] = useState(new ThingStore())

  return <ThingStoreContext.Provider value={thingStore}>{children}</ThingStoreContext.Provider>
}
