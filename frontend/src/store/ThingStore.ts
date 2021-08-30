import Thing, {ThingState} from '../material/Thing'
import ThingName from '../material/ThingName'
import Id from '../material/Id'
import {ALL_THINGS, getActiveStateUrl} from './communication/backendUrlCreator'
import {getJson} from './communication/restClient'
import ThingProperty from '../material/ThingProperty'
import Location from '../material/Location'
import LocationWithAddress from '../material/LocationWithAddress'
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

  private _datastreamStore: DatastreamStore

  constructor(datastreamStore: DatastreamStore) {
    this._things = new Map<string, Thing>()
    this._datastreamStore = datastreamStore
    const {getThingsFromBackend} = this
    getThingsFromBackend()
  }

  get things(): Promise<Array<Thing>> {
    const {getThingsFromBackend} = this

    const resultPromise = new Promise<Array<Thing>>((resolve, reject) => {
      getThingsFromBackend().then(things => {
        const result = new Array<Thing>()

        things.forEach(e => {
          result.push(e)
        })

        resolve(result)
      })
    })

    return resultPromise
  }

  get cachedThings(): Array<Thing> {
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
  private getThingsFromBackend = (): Promise<Map<string, Thing>> => {
    const {_things, createThing, applyProperties, parseLocation} = this

    const resultPromise = new Promise<Map<string, Thing>>((resolve, reject) => {
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

        resolve(_things)
      })
    })

    return resultPromise
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
      isActive(): Promise<ThingState> {
        const resultPromise = new Promise<ThingState>((resolve, reject) => {
          getJson(getActiveStateUrl(id))
            .then(thingJSON => {
              const jsonState = thingJSON[0]
              const activeState =
                jsonState === true ? ThingState.Online : jsonState === false ? ThingState.Offline : ThingState.Unknown
              resolve(activeState)
            })
            .catch(() => {
              resolve(ThingState.Unknown)
            })
        })

        return resultPromise
      }

      getDatastreams(): Promise<Array<Datastream>> {
        return _datastreamStore.getDatastreams(id)
      }
    })(name, id, location)

    return result
  }

  getThing = (id: Id): Promise<Thing> => {
    const {things} = this

    const resultPromise = new Promise<Thing>((resolve, reject) => {
      things.then(thingsList => {
        const {_things} = this
        const result = _things.get(id.toString())
        if (result) {
          resolve(result)
        }
        reject()
      })
    })

    return resultPromise
  }
}

export default ThingStore
