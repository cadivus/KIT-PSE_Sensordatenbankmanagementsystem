import Unit from './Unit'

/**
 * This is a representation for thing values.
 */
class SensorValue {
  /**
   * Thing values stored as an Integer.
   */
  readonly value: string

  readonly unit: Unit

  constructor(value: string, unit: Unit) {
    this.value = value
    this.unit = unit
  }

  toString(): string {
    const {value, unit} = this
    return `${value} ${unit.toString()}`
  }

  valueToString(): string {
    const {value} = this
    return `${value}`
  }
}

export default SensorValue
