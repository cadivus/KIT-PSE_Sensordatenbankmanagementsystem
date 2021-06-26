import Replay from '../material/Replay'
import Sensor from '../material/Sensor'

/**
 * This is the storage for replays.
 * It holds all the replay objects, gets data from the backend and synchronizes data.
 */
class ReplayStore {
    /**
     * Requests a replay from the backend
     * 
     * @param replay The replay to request
     * @return True on success, false on failure
     */
    private requestReplay(replay: Replay): boolean {
        return false
    }
    
    /**
     * Creates a replay for the specified sensors
     * 
     * @param sensors Sensor to be replayed.
     * @return The created object
     */
    createReplay = (sensors: Array<Sensor>): Replay | null => {
        return null
    }
}

export default ReplayStore
