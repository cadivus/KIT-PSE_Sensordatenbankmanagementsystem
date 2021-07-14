import {useContext} from 'react'
import {SubscriptionStoreContext} from '../context/SubscriptionStoreProvider'

const useSubscriptionStore = () => useContext(SubscriptionStoreContext)

export default useSubscriptionStore
