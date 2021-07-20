import {useContext} from 'react'
import {ReplayStoreContext} from '../context/ReplayStoreProvider'

const useReplayStore = () => useContext(ReplayStoreContext)

export default useReplayStore
