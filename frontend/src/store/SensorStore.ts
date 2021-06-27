import Sensor from '../material/Sensor'

/**
 * This is the storage for sensors.
 * It holds all the sensor objects, gets data from the backend and synchronizes data.
 */
class SensorStore {
    /**
     * Holds every sensor object.
     * It will be write protected for store users.
     */
    private _sensors: Array<Sensor> = []

    get sensors(): Array<Sensor> {
        return this._sensors
    }
    
    /**
     * Gets sensors from the backend.
     */
    private getSensorsFromBackend(): void {
        
    }
}

export default SensorStore
