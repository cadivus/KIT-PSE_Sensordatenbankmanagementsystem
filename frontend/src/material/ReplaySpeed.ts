/**
 * This class represents a replay speed
 */
class ReplaySpeed {
  readonly speed: number

  constructor(speed: number) {
    this.speed = speed
  }

  toString(): string {
    const {speed} = this
    return `${speed}`
  }

  toNumber(): number {
    const {speed} = this
    return speed
  }
}

export default ReplaySpeed
