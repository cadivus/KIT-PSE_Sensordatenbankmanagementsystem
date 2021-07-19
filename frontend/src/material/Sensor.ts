import SensorValue from './SensorValue'
import SensorName from './SensorName'
import Id from './Id'

/**
 * this class represents a sensor.
 */
abstract class Sensor {
  /**
   * Name of the sensor
   */
  readonly name: SensorName

  /**
   * Id of the sensor
   */
  readonly id: Id

  constructor(name: SensorName, id: Id) {
    this.name = name
    this.id = id
  }

  /**
   * This function gets the sensors value.
   * It is abstract for being implemented by the SensorStore.
   *
   * @return The current value of the sensor.
   */
  public abstract getValue(): SensorValue
}

export default Sensor
