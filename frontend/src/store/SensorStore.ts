import Sensor from '../material/Sensor'
import SensorValue from '../material/SensorValue'
import SensorName from '../material/SensorName'
import Id from '../material/Id'

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
   * Gets sensors from the backend.
   */
  private getSensorsFromBackend = (): void => {
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
  }
}

export default SensorStore
