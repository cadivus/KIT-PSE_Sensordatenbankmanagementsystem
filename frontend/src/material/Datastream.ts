import Unit from './Units'
import Id from './Id'
import DatastreamRow from './DatastreamRow'

abstract class Datastream {
  readonly unit: Unit

  readonly datastreamId: Id

  constructor(datastreamId: Id, unit: Unit) {
    this.unit = unit
    this.datastreamId = datastreamId
  }

  public abstract getAllValues(): Promise<Array<DatastreamRow>>
}

export default Datastream
