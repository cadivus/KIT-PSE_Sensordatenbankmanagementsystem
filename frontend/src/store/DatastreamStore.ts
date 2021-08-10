import Datastream from '../material/Datastream'
import DatastreamRow from '../material/DatastreamRow'
import Id from '../material/Id'
import Unit from '../material/Unit'
import {getJson, getText} from './communication/restClient'
import {getAllThingDatastreamsUrl, getExportDatastreamUrl} from './communication/backendUrlCreator'
import DatastreamName from '../material/DatastreamName'
import SensorValue from '../material/SensorValue'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const csvToJson = require('convert-csv-to-json')

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
      getAllValues(limit = 0): Promise<Array<DatastreamRow>> {
        const url = getExportDatastreamUrl(id, limit)

        const resultPromise = new Promise<Array<DatastreamRow>>((resolve, reject) => {
          getText(url)
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
            .then((csv: any): Array<DatastreamRow> => {
              const result = new Array<DatastreamRow>()

              const json = csvToJson.fieldDelimiter(',').csvStringToJson(csv)
              // eslint-disable-next-line @typescript-eslint/no-explicit-any
              json.forEach((row: any) => {
                const date = new Date(row.RESULTTIME)
                const value = new SensorValue(Number(row.RESULTNUMBER), unit)

                const datastreamRow: DatastreamRow = {value, date}
                result.push(datastreamRow)
              })

              return result
            })
            .then(result => {
              resolve(result)
            })
        })
        return resultPromise
      }
    })(id, new Unit('unknown'), name)
  }
}

export default DatastreamStore
