/**
 * This class represents a sensors name.
 */
class SensorName {
  readonly name: string

  constructor(name: string) {
    this.name = name
  }

  toString = (): string => {
    const {name} = this
    return name
  }
}

export default SensorName
