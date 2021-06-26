import Sensor from './Sensor'

/**
 * This class represents a replay.
 */
class Replay {
    /**
     * This list contains all sensors being part of the replay.
     */
    sensors: Array<Sensor>
    
    /**
     * Speed multiplier
     */
    speed: number
    
    /**
     * Indicates whether the replay is running.
     */
    running: boolean

    /**
     * The ActionListener will be triggered on every change.
     * 
     * @param actionListener The ActionListener to be triggered.
     */
    onChange = (actionListener: any): void => {
        
    }
    
    /**
     * Starts the replay.
     */
    start = (): void => {
        // Nothing yet
    }
    
    /**
     * Stops the replay
     */
    stop = (): void => {
        // Nothing yet
    }
    
    /**
     * Adds a sensor to the replay.
     * 
     * @param sensor Sensor to add
     */
    addSensor = (sensor: Sensor): void => {
        
    }

    /**
     * Removes a sensor from the replay.
     * 
     * @param sensor Sensor to remove
     * @return True on success, false on failure.
     */
    removeSensor = (sensor: Sensor): boolean => {
        return false
    }
}

export default Replay
