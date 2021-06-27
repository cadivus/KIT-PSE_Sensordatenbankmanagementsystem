import SensorValue from './SensorValue'
import SensorName from './SensorName'

/**
 * this class represents a sensor.
 */
abstract class Sensor {
  /**
  * Name of the sensor
  */
  name: SensorName = new SensorName("Sensor")

  constructor(name: SensorName) {
    this.name = name
  }

  /**
  * This function gets the sensors value.
  * It is abstract for being implemented by the SensorStore.
  *
  * @return The current value of the sensor.
  */
  public abstract getValue(): SensorValue
}

export default  Sensor
