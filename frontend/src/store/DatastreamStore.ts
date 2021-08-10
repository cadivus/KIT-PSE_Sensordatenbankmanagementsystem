import Datastream from '../material/Datastream'
import DatastreamRow from '../material/DatastreamRow'
import Id from '../material/Id'
import Unit from '../material/Unit'
import {getJson} from './communication/restClient'
import {getAllThingDatastreamsUrl} from './communication/backendUrlCreator'
import DatastreamName from '../material/DatastreamName'

class DatastreamStore {
  private _datastreams: Map<string, Datastream>

  constructor() {
    this._datastreams = new Map<string, Datastream>()
  }

  public getDatastreams = (thingId: Id): Promise<Array<Datastream>> => {
    const {_datastreams, implementDatastream} = this
    const url = getAllThingDatastreamsUrl(thingId)

    const resultPromise = new Promise<Array<Datastream>>((resolve, reject) => {
      getJson(url)
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        .then((datastreamJSON: any): Array<Datastream> => {
          const result = new Array<Datastream>()

          // eslint-disable-next-line @typescript-eslint/no-explicit-any
          datastreamJSON.forEach((element: any) => {
            const id = new Id(element.id)
            const unit = new Unit(element.unit)
            const name = new DatastreamName(element.name)

            let existingDatastream = _datastreams.get(id.toString())
            if (!existingDatastream) {
              existingDatastream = implementDatastream(id, unit, name)
              _datastreams.set(id.toString(), existingDatastream)
            }
            result.push(existingDatastream)
          })

          return result
        })
        .then((result: Array<Datastream>): void => {
          resolve(result)
        })
    })

    return resultPromise
  }

  getDatastream = (id: Id): Datastream | undefined => {
    const {_datastreams} = this
    return _datastreams.get(id.toString())
  }

  private implementDatastream = (id: Id, unit: Unit, name: DatastreamName): Datastream => {
    return new (class extends Datastream {
      getAllValues(): Promise<Array<DatastreamRow>> {
        return Promise.resolve(new Array<DatastreamRow>())
      }
    })(id, new Unit('unknown'), name)
  }
}

export default DatastreamStore
