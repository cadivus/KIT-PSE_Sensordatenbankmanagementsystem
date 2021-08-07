import Thing from './Thing'

/**
* This class represents a replay.
*/
class Replay {
  /**
  * This list contains all things being part of the replay.
  */
  things: Array<Thing> = []

  /**
  * Speed multiplier
  */
  speed: number = 1

  /**
  * Indicates whether the replay is running.
  */
  running: boolean = false

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
  * Adds a thing to the replay.
  *
  * @param thing Thing to add
  */
  addThing = (thing: Thing): void => {

  }

  /**
  * Removes a thing from the replay.
  *
  * @param thing Thing to remove
  * @return True on success, false on failure.
  */
  removeThing = (thing: Thing): boolean => {
    return false
  }
}

export default Replay
