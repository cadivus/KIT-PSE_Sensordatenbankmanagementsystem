import Datastream from '../material/Datastream'
import DatastreamRow from '../material/DatastreamRow'
import Id from '../material/Id'
import Thing from '../material/Thing'
import Unit from '../material/Units'

class DatastreamStore {
  private _datastreams: Map<string, Datastream>

  constructor() {
    this._datastreams = new Map<string, Datastream>()
  }

  getDatastreams = (thing: Thing): Array<Datastream> => {
    return new Array<Datastream>()
  }

  private getDatastream = (id: Id): Datastream => {
    const {implementDatastream} = this
    return implementDatastream(id)
  }

  private implementDatastream = (id: Id): Datastream => {
    return new (class extends Datastream {
      getAllValues(): Promise<Array<DatastreamRow>> {
        return Promise.resolve(new Array<DatastreamRow>())
      }
    })(id, Unit.DEGREES_CELSIUS)
  }
}

export default DatastreamStore
