/**
 * This class represents a things name.
 */
class ThingName {
  readonly name: string

  constructor(name: string) {
    this.name = name
  }

  toString(): string {
    const {name} = this
    return name
  }
}

export default ThingName
