import React, {createContext, FC, useState} from 'react'
import SensorStore from '../store/SensorStore'

export const SensorStoreContext = createContext<SensorStore | undefined>(undefined)

export const SensorStoreProvider: FC = ({children}) => {
  const [sensorStore] = useState(new SensorStore())

  return <SensorStoreContext.Provider value={sensorStore}>{children}</SensorStoreContext.Provider>
}
