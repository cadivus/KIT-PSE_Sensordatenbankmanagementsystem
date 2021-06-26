/**
 * This is the storage for sensors.
 * It holds all the sensor objects, gets data from the backend and synchronizes data.
 */
import Sensor from '../material/Sensor'

class SensorStore {
    /**
     * Holds every sensor object.
     * It will be write protected for store users.
     */
    sensors: Array<Sensor> = []
    
    /**
     * Gets sensors from the backend.
     */
    private getSensorsFromBackend(): void {
        
    }
}

export default SensorStore
