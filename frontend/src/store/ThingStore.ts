import Thing, {ThingState} from '../material/Thing'
import SensorValue from '../material/SensorValue'
import ThingName from '../material/ThingName'
import Id from '../material/Id'
import {ALL_THINGS, getActiveStateUrl} from './communication/backendUrlCreator'
import {getJson} from './communication/restClient'
import ThingProperty from '../material/ThingProperty'
import Location from '../material/Location'
import LocationWithAddress from '../material/LocationWithAddress'
import Unit from '../material/Unit'
import DatastreamStore from './DatastreamStore'
import Datastream from '../material/Datastream'

/**
 * This is the storage for things.
 * It holds all the thing objects, gets data from the backend and synchronizes data.
 */
class ThingStore {
  /**
   * Holds every thing object.
   * It will be write protected for store users.
   */
  private _things: Map<string, Thing>

  private _lastUpdate = 0

  private _datastreamStore: DatastreamStore

  constructor(datastreamStore: DatastreamStore) {
    this._things = new Map<string, Thing>()
    this._datastreamStore = datastreamStore
    const {getThingsFromBackend} = this
    getThingsFromBackend()
  }

  get things(): Array<Thing> {
    const {getThingsFromBackend} = this
    getThingsFromBackend()

    const {_things} = this
    const result = new Array<Thing>()

    _things.forEach(e => {
      result.push(e)
    })

    return result
  }

  /**
   * Gets things from the backend.
   */
  private getThingsFromBackend = (): void => {
    const {_things, createThing, applyProperties, parseLocation} = this
    getJson(ALL_THINGS).then(thingJSON => {
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
      thingJSON.forEach((element: any) => {
        const id = new Id(element.id)
        const name = new ThingName(element.name)

        let existingThing = _things.get(id.toString())
        if (!existingThing) {
          let location = new Location(0, 0)
          if (element.locations && element.locations[0]) {
            const {name: address} = element.locations[0]
            const jsonString = element.locations[0].location
            location = parseLocation(jsonString, address)
          }
          existingThing = createThing(id, name, location)
          _things.set(id.toString(), existingThing)
        } else {
          existingThing.name = name
        }
        if (element.properties !== null && element.properties !== 'null') {
          applyProperties(existingThing, element.properties)
        }
        existingThing.description = element.description ? element.description : ''
      })

      this._lastUpdate = Date.now()
    })
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  private applyProperties = (thing: Thing, jsonProperties: string) => {
    JSON.parse(jsonProperties, (key, value) => {
      if (key === '') return
      const property = new ThingProperty(key, `${value}`)
      thing.setProperty(property)
    })
  }

  private parseLocation = (jsonString: string, name: string): Location => {
    const parsedJson = JSON.parse(jsonString)
    const {coordinates} = parsedJson
    const x = coordinates[0]
    const y = coordinates[1]

    if (name && name !== '') {
      return new LocationWithAddress(x, y, name)
    }
    return new Location(x, y)
  }

  private createThing = (id: Id, name: ThingName, location: Location): Thing => {
    const {_datastreamStore} = this

    const result = new (class extends Thing {
      private activeState = ThingState.Unknown

      getValue(): SensorValue {
        return new SensorValue(100, new Unit('unknown'))
      }

      isActive(): ThingState {
        try {
          getJson(getActiveStateUrl(id)).then(thingJSON => {
            const jsonState = thingJSON[0]
            this.activeState =
              jsonState === true ? ThingState.Online : jsonState === false ? ThingState.Offline : ThingState.Unknown
          })
        } catch (e) {
          this.activeState = ThingState.Unknown
        }

        const {activeState} = this
        return activeState
      }

      getDatastreams(): Promise<Array<Datastream>> {
        return _datastreamStore.getDatastreams(id)
      }
    })(name, id, location)

    result.isActive()

    return result
  }

  getThing = (id: Id): Thing | undefined => {
    const {_things} = this

    return _things.get(id.toString())
  }

  get lastUpdate(): number {
    const {_lastUpdate} = this
    return _lastUpdate
  }
}

export default ThingStore
