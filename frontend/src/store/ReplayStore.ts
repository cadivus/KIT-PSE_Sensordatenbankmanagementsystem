import Replay from '../material/Replay'
import Thing from '../material/Thing'
import ReplaySpeed from '../material/ReplaySpeed'

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
  private requestReplay(start: Date, end: Date, speed: ReplaySpeed, things: Set<Thing>): Promise<Replay | null> {
    return Promise.resolve(null)
  }

  /**
   * Creates a replay for the specified things
   *
   * @param things Thing to be replayed.
   * @return The created object or null on failure
   */
  createReplay = (start: Date, end: Date, speed: ReplaySpeed, things: Set<Thing>): Promise<Replay | null> => {
    const {requestReplay} = this

    const resultPromise = new Promise<Replay | null>((resolve, reject) => {
      requestReplay(start, end, speed, things).then((result: Replay | null) => {
        resolve(result)
      })
    })

    return resultPromise
  }
}

export default ReplayStore
