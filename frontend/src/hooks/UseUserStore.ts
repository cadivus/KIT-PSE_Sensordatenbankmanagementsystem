import {useContext} from 'react'
import {UserStoreContext} from '../context/UserStoreProvider'

const useUserStore = () => useContext(UserStoreContext)

export default useUserStore
