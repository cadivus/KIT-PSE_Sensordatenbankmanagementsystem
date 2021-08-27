import React, {createContext, FC, useEffect, useState} from 'react'
import ThingStore from '../store/ThingStore'
import useDatastreamStore from '../hooks/UseDatastreamStore'

export const ThingStoreContext = createContext<ThingStore | undefined>(undefined)

export const ThingStoreProvider: FC = ({children}) => {
  const datastreamStore = useDatastreamStore()

  const [thingStore, setThingStore] = useState<ThingStore | undefined>(undefined)

  useEffect(() => {
    if (datastreamStore) setThingStore(new ThingStore(datastreamStore))
  }, [setThingStore, datastreamStore])

  return <ThingStoreContext.Provider value={thingStore}>{children}</ThingStoreContext.Provider>
}
