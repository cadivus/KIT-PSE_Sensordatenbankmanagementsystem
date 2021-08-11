import Thing from './Thing'
import ReplaySpeed from './ReplaySpeed'
import Id from './Id'

/**
 * This class represents a replay.
 */
class Replay {
  /**
   * Id of the replay
   */
  readonly id: Id

  /**
   * This list contains all things being part of the replay.
   */
  readonly things: Set<Thing>

  /**
   * Speed multiplier
   */
  readonly speed: ReplaySpeed

  /**
   * Start time and date of the replay
   */
  readonly start: Date

  /**
   * End time and date of the replay
   */
  readonly end: Date

  /**
   * Creates a new onject representing a replay
   *
   * @param id Id of the replay
   * @param start Start time of the replay
   * @param end End time of the replay
   * @param speed Speed of the replay
   * @param things Things being part of the replay
   */
  constructor(id: Id, start: Date, end: Date, speed: ReplaySpeed, things: Set<Thing>) {
    this.id = id
    this.start = start
    this.end = end
    this.speed = speed
    this.things = things
  }
}

export default Replay
