import Sensor, {SensorState} from '../material/Sensor'
import SensorValue from '../material/SensorValue'
import SensorName from '../material/SensorName'
import Id from '../material/Id'
import {ALL_THINGS, getActiveStateUrl} from './communication/backendUrlCreator'
import {getJson} from './communication/restClient'
import SensorProperty from '../material/SensorProperty'
import Location from '../material/Location'

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
      const location = new Location(10 * i, 1000 * i)
      this._sensors.set(
        id.toString(),
        new (class extends Sensor {
          getValue(): SensorValue {
            return new SensorValue(i * 10)
          }

          isActive(): SensorState {
            return SensorState.Unknown
          }
        })(new SensorName(`Sensor${i}`), id, location),
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

    const {_sensors, createSensor, applyProperties} = this
    getJson(ALL_THINGS).then(sensorJSON => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      sensorJSON.forEach((element: any) => {
        const id = new Id(element.id)
        const name = new SensorName(element.name)

        let existingSensor = _sensors.get(id.toString())
        if (!existingSensor) {
          const location = new Location(10, 20)
          existingSensor = createSensor(id, name, location)
          _sensors.set(id.toString(), existingSensor)
        } else {
          existingSensor.name = name
        }
        if (element.properties !== null && element.properties !== 'null') {
          applyProperties(existingSensor, element.properties)
        }
        existingSensor.description = element.description ? element.description : ''
      })

      this._lastUpdate = Date.now()
    })
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private applyProperties = (sensor: Sensor, jsonProperties: string) => {
    JSON.parse(jsonProperties, (key, value) => {
      if (key === '') return
      const property = new SensorProperty(key, `${value}`)
      sensor.setProperty(property)
    })
  }

  private createSensor = (id: Id, name: SensorName, location: Location): Sensor => {
    const result = new (class extends Sensor {
      private activeState = SensorState.Unknown

      getValue(): SensorValue {
        return new SensorValue(100)
      }

      isActive(): SensorState {
        try {
          getJson(getActiveStateUrl(id)).then(sensorJSON => {
            const jsonState = sensorJSON[0]
            this.activeState =
              jsonState === true ? SensorState.Online : jsonState === false ? SensorState.Offline : SensorState.Unknown
          })
        } catch (e) {
          this.activeState = SensorState.Unknown
        }

        const {activeState} = this
        return activeState
      }
    })(name, id, location)

    result.isActive()

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
