import {useContext} from 'react'
import {SensorStoreContext} from '../context/SensorStoreProvider'

const useSensorStore = () => useContext(SensorStoreContext)

export default useSensorStore
