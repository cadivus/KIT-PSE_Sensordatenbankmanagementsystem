/**
 * This class represents a datastream name.
 */
class DatastreamName {
  readonly name: string

  constructor(name: string) {
    this.name = name
  }

  toString(): string {
    const {name} = this
    return name
  }
}

export default DatastreamName
