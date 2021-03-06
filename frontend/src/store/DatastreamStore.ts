import Datastream from '../types/Datastream'
import DatastreamRow from '../types/DatastreamRow'
import Id from '../types/Id'
import Unit from '../types/Unit'
import {getJson, getText} from './communication/restClient'
import {getAllThingDatastreamsUrl, getDatastreamUrl, getExportDatastreamUrl} from './communication/backendUrlCreator'
import DatastreamName from '../types/DatastreamName'
import SensorValue from '../types/SensorValue'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const csvToJson = require('convert-csv-to-json')

class DatastreamStore {
  private _datastreams: Map<string, Datastream>

  constructor() {
    this._datastreams = new Map<string, Datastream>()
  }

  public getDatastreams = (thingId: Id): Promise<Array<Datastream>> => {
    const {_datastreams, implementDatastream, parseDatastream} = this
    const url = getAllThingDatastreamsUrl(thingId)

    const resultPromise = new Promise<Array<Datastream>>((resolve, reject) => {
      getJson(url)
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        .then((datastreamJSON: any): Array<Datastream> => {
          const result = new Array<Datastream>()

          // eslint-disable-next-line @typescript-eslint/no-explicit-any
          datastreamJSON.forEach((element: any) => {
            const datastream = parseDatastream(element)
            result.push(datastream)
          })

          return result
        })
        .then((result: Array<Datastream>): void => {
          resolve(result)
        })
    })

    return resultPromise
  }

  getDatastream = (id: Id): Promise<Datastream> => {
    const {_datastreams, parseDatastream} = this

    const resultPromise = new Promise<Datastream>((resolve, reject) => {
      const datastream = _datastreams.get(id.toString())
      if (datastream) {
        resolve(datastream)
      } else {
        const url = getDatastreamUrl(id)
        getJson(url)
          .then(json => {
            const result = parseDatastream(json)
            resolve(result)
          })
          .catch(() => {
            reject()
          })
      }
    })

    return resultPromise
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private parseDatastream = (element: any): Datastream => {
    const {_datastreams, implementDatastream} = this
    const id = new Id(element.id)
    const unit = new Unit(element.unit)
    const name = new DatastreamName(element.name)

    let existingDatastream = _datastreams.get(id.toString())
    if (!existingDatastream) {
      existingDatastream = implementDatastream(id, unit, name)
      _datastreams.set(id.toString(), existingDatastream)
    }

    return existingDatastream
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
                let dateString = row.RESULTTIME
                if (dateString === '') dateString = row.PHENOMENONEND

                const date = new Date(dateString)
                const value = new SensorValue(row.RESULTNUMBER, unit)

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
    })(id, unit, name)
  }
}

export default DatastreamStore
