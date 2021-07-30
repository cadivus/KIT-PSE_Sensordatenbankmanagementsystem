import SensorValue from './SensorValue'
import SensorName from './SensorName'
import Id from './Id'
import SensorProperty from './SensorProperty'

/**
 * this class represents a sensor.
 */
abstract class Sensor {
  /**
   * Name of the sensor
   */
  name: SensorName

  /**
   * Id of the sensor
   */
  readonly id: Id

  private _properties: Map<string, SensorProperty>

  constructor(name: SensorName, id: Id) {
    this._properties = new Map<string, SensorProperty>()
    this.name = name
    this.id = id
  }

  get properties(): Array<SensorProperty> {
    const {_properties} = this
    const result = new Array<SensorProperty>()

    _properties.forEach(e => {
      result.push(e)
    })

    return result
  }

  setProperty = (property: SensorProperty): void => {
    const {_properties} = this
    const oldProperty = _properties.get(property.key)
    if (oldProperty && oldProperty.value === property.value) return

    _properties.set(property.key, property)
  }

  /**
   * This function gets the sensors value.
   * It is abstract for being implemented by the SensorStore.
   *
   * @return The current value of the sensor.
   */
  public abstract getValue(): SensorValue

  /**
   * This function indicates whether the sensor is active.
   *
   * @return true, when the sensor is active. false otherwise
   */
  public abstract isActive(): boolean
}

export default Sensor
