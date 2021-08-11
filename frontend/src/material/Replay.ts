import Thing from './Thing'
import ReplaySpeed from './ReplaySpeed'

/**
* This class represents a replay.
*/
class Replay {
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

  constructor(start: Date, end: Date, speed: ReplaySpeed, things: Set<Thing>) {
    this.start = start
    this.end = end
    this.speed = speed
    this.things = things
  }
}

export default Replay
