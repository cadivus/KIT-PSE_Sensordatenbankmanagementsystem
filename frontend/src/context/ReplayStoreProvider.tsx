import React, {FC, createContext, useState} from 'react'
import ReplayStore from '../store/ReplayStore'

export const ReplayStoreContext = createContext<ReplayStore | undefined>(undefined)

export const ReplayStoreProvider: FC = ({children}) => {
  const [replayStore] = useState(new ReplayStore())

  return <ReplayStoreContext.Provider value={replayStore}>{children}</ReplayStoreContext.Provider>
}
