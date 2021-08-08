/**
 * This is a representation for thing values.
 */
class ThingValue {
  /**
   * Thing values stored as an Integer.
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

export default ThingValue
