import {useContext} from 'react'
import {ThingStoreContext} from '../context/ThingStoreProvider'

const useThingStore = () => useContext(ThingStoreContext)

export default useThingStore
