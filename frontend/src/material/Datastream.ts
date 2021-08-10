import Unit from './Unit'
import Id from './Id'
import DatastreamRow from './DatastreamRow'
import DatastreamName from './DatastreamName'

abstract class Datastream {
  readonly unit: Unit

  readonly datastreamId: Id

  readonly name: DatastreamName

  constructor(datastreamId: Id, unit: Unit, name: DatastreamName) {
    this.unit = unit
    this.datastreamId = datastreamId
    this.name = name
  }

  public abstract getAllValues(): Promise<Array<DatastreamRow>>
}

export default Datastream
