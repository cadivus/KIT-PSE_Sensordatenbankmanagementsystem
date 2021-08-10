import {useContext} from 'react'
import {DatastreamStoreContext} from '../context/DatastreamStoreProvider'

const useDatastreamStore = () => useContext(DatastreamStoreContext)

export default useDatastreamStore
