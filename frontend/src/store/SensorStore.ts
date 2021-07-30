import Sensor from '../material/Sensor'
import SensorValue from '../material/SensorValue'
import SensorName from '../material/SensorName'
import Id from '../material/Id'
import {ALL_THINGS} from './communication/backendUrlCreator'
import {getJson} from './communication/restClient'

/**
 * This is the storage for sensors.
 * It holds all the sensor objects, gets data from the backend and synchronizes data.
 */
class SensorStore {
  /**
   * Holds every sensor object.
   * It will be write protected for store users.
   */
  private _sensors: Map<string, Sensor>

  private _lastUpdate = 0

  constructor() {
    this._sensors = new Map<string, Sensor>()
    const {getSensorsFromBackend} = this
    getSensorsFromBackend()
  }

  get sensors(): Array<Sensor> {
    const {getSensorsFromBackend} = this
    getSensorsFromBackend()

    const {_sensors} = this
    const result = new Array<Sensor>()

    _sensors.forEach(e => {
      result.push(e)
    })

    return result
  }

  /**
   * Gets mock sensors.
   */
  private getMockSensors = (): void => {
    const {_sensors} = this
    if (_sensors && _sensors.size > 0) return

    const mockSensor = (i: number) => {
      const id = new Id(`${i}-${new Date().getTime() / 1000}`)
      this._sensors.set(
        id.toString(),
        new (class extends Sensor {
          getValue(): SensorValue {
            return new SensorValue(i * 10)
          }
        })(new SensorName(`Sensor${i}`), id),
      )
    }

    for (let i = 0; i < 20; i += 1) {
      mockSensor(i)
    }

    this._lastUpdate = Date.now()
  }

  /**
   * Gets sensors from the backend.
   */
  private getSensorsFromBackend = (): void => {
    const {env} = process
    if (env.USE_MOCK) {
      const {getMockSensors} = this
      getMockSensors()
      return
    }

    const {_sensors, createSensor} = this
    let i = 10

    getJson(ALL_THINGS).then(sensorJSON => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      sensorJSON.forEach((element: any) => {
        i += 1
        const id = new Id(element.id)
        const name = new SensorName(element.name)

        const existingSensor = _sensors.get(id.toString())
        if (!existingSensor) {
          _sensors.set(id.toString(), createSensor(id, name))
        } else {
          const sensor = _sensors.get(id.toString())
          existingSensor.name = name
        }
      })

      this._lastUpdate = Date.now()
    })
  }

  private createSensor = (id: Id, name: SensorName): Sensor => {
    const result = new (class extends Sensor {
      getValue(): SensorValue {
        return new SensorValue(100)
      }
    })(name, id)

    return result
  }

  getSensor = (id: Id): Sensor | undefined => {
    const {_sensors} = this

    return _sensors.get(id.toString())
  }

  get lastUpdate(): number {
    const {_lastUpdate} = this
    return _lastUpdate
  }
}

export default SensorStore
