/**
 * This is a representation for sensor values.
 */
class SensorValue {
  /**
   * Sensor values stored as an Integer.
   */
  readonly value: number

  constructor(value: number) {
    this.value = value
  }

  toString(): string {
    const {value} = this
    return `${value}`
  }
}

export default SensorValue
