import Thing, {ThingState} from '../material/Thing'
import SensorValue from '../material/SensorValue'
import ThingName from '../material/ThingName'
import Id from '../material/Id'
import {ALL_THINGS, getActiveStateUrl} from './communication/backendUrlCreator'
import {getJson} from './communication/restClient'
import ThingProperty from '../material/ThingProperty'
import Location from '../material/Location'
import LocationWithAddress from '../material/LocationWithAddress'
import Unit from '../material/Units'
import DatastreamStore from './DatastreamStore'

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
   * Gets mock things.
   */
  private getMockThings = (): void => {
    const {_things} = this
    if (_things && _things.size > 0) return

    const mockThing = (i: number) => {
      const id = new Id(`${i}-${new Date().getTime() / 1000}`)
      const location = new Location(10 * i, 1000 * i)
      this._things.set(
        id.toString(),
        new (class extends Thing {
          getValue(): SensorValue {
            return new SensorValue(i * 10, Unit.DEGREES_CELSIUS)
          }

          isActive(): ThingState {
            return ThingState.Unknown
          }
        })(new ThingName(`Thing${i}`), id, location),
      )
    }

    for (let i = 0; i < 20; i += 1) {
      mockThing(i)
    }

    this._lastUpdate = Date.now()
  }

  /**
   * Gets things from the backend.
   */
  private getThingsFromBackend = (): void => {
    const {env} = process
    if (env.USE_MOCK) {
      const {getMockThings} = this
      getMockThings()
      return
    }

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
    const result = new (class extends Thing {
      private activeState = ThingState.Unknown

      getValue(): SensorValue {
        return new SensorValue(100, Unit.DEGREES_CELSIUS)
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
