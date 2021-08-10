import Unit from './Unit'

/**
 * This is a representation for thing values.
 */
class SensorValue {
  /**
   * Thing values stored as an Integer.
   */
  readonly value: number

  readonly unit: Unit

  constructor(value: number, unit: Unit) {
    this.value = value
    this.unit = unit
  }

  toString(): string {
    const {value, unit} = this
    return `${value} ${unit.toString()}`
  }
}

export default SensorValue
