import SensorValue from './SensorValue'
import ThingName from './ThingName'
import Id from './Id'
import ThingProperty from './ThingProperty'
import Location from './Location'
import Datastream from './Datastream'

export enum ThingState {
  Unknown,
  Offline,
  Online,
}

/**
 * this class represents a thing.
 */
abstract class Thing {
  /**
   * Name of the thing
   */
  name: ThingName

  /**
   * Id of the thing
   */
  readonly id: Id

  private _properties: Map<string, ThingProperty>

  description = ''

  readonly location: Location

  constructor(name: ThingName, id: Id, location: Location) {
    this._properties = new Map<string, ThingProperty>()
    this.name = name
    this.id = id
    this.location = location
  }

  get properties(): Array<ThingProperty> {
    const {_properties} = this
    const result = new Array<ThingProperty>()

    _properties.forEach(e => {
      result.push(e)
    })

    return result
  }

  setProperty(property: ThingProperty): void {
    const {_properties} = this
    const oldProperty = _properties.get(property.key)
    if (oldProperty && oldProperty.value === property.value) return

    _properties.set(property.key, property)
  }

  /**
   * This function indicates whether the thing is active.
   *
   * @return true, when the thing is active. false otherwise
   */
  public abstract isActive(): Promise<ThingState>

  public abstract getDatastreams(): Promise<Array<Datastream>>
}

export default Thing
