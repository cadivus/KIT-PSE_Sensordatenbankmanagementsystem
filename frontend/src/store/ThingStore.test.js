import ThingStore from './ThingStore'
import DatastreamStore from './DatastreamStore'
import {getJson} from './communication/restClient'
import {getJson as getJsonMock} from '../test/mock/store/communication/restClientMock'
import {thingCollectionMatches} from '../test/matchTest/material/ThingMatch'
import {allThings, sensor1Id, sensor2Id, sensor3Id} from '../test/mock/store/communication/mockData/backend/getJson'
import Id from '../material/Id'
import {ThingState} from '../material/Thing'

jest.mock('./communication/restClient')

const initValue = () => {
  getJson.mockImplementation(param => {
    return getJsonMock(param)
  })

  const thingStore = new ThingStore(new DatastreamStore())

  return thingStore
}

describe('fetching data', () => {
  it('check data length', async () => {
    const store = initValue()

    const fetchedThings = await store.things
    expect(fetchedThings.length).toBe(3)
  })

  it('check data match', async () => {
    const store = initValue()
    const fetchedThings = await store.things
    thingCollectionMatches(fetchedThings, allThings)
  })
})

describe('cache', () => {
  it('check cache length', async () => {
    const store = initValue()

    await store.things
    const cachedThings = store.cachedThings
    expect(cachedThings.length).toBe(3)
  })

  it('check cache data match', async () => {
    const store = initValue()
    await store.things
    const cachedThings = store.cachedThings

    thingCollectionMatches(cachedThings, allThings)
  })
})

describe('thing implementations', () => {
  it('check online state', async () => {
    const store = initValue()

    const activeThing = await store.getThing(new Id(sensor1Id))
    const activeThingState = await activeThing.isActive()
    expect(activeThingState).toBe(ThingState.Online)
  })

  it('check offline state', async () => {
    const store = initValue()

    const inactiveThing = await store.getThing(new Id(sensor2Id))
    const inactiveThingState = await inactiveThing.isActive()
    expect(inactiveThingState).toBe(ThingState.Offline)
  })

  it('check unknown state', async () => {
    const store = initValue()

    const unknownThing = await store.getThing(new Id(sensor3Id))
    const unknownThingState = await unknownThing.isActive()
    expect(unknownThingState).toBe(ThingState.Unknown)
  })
})
