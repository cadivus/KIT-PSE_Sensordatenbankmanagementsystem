import ThingStore from './ThingStore'
import DatastreamStore from './DatastreamStore'
import {getJson, getText, postJsonGetText} from './communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {thingCollectionMatches} from '../../test/matchTest/material/ThingMatch'
import {
  allThings,
  thing1Datastreams,
  thing2Datastreams,
  thing3Datastreams,
  thing1Id,
  thing2Id,
  thing3Id,
} from '../../test/mock/store/communication/mockData/backend/getJson'
import Id from '../types/Id'
import {ThingState} from '../types/Thing'
import {datastreamCollectionMatches} from '../../test/matchTest/material/DatastreamMatch'

jest.mock('./communication/restClient')

const initValue = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

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
    const {cachedThings} = store
    expect(cachedThings.length).toBe(3)
  })

  it('check cache data match', async () => {
    const store = initValue()
    await store.things
    const {cachedThings} = store

    thingCollectionMatches(cachedThings, allThings)
  })
})

describe('thing implementations', () => {
  it('check online state', async () => {
    const store = initValue()

    const activeThing = await store.getThing(new Id(thing1Id))
    const activeThingState = await activeThing.isActive()
    expect(activeThingState).toBe(ThingState.Online)
  })

  it('check offline state', async () => {
    const store = initValue()

    const inactiveThing = await store.getThing(new Id(thing2Id))
    const inactiveThingState = await inactiveThing.isActive()
    expect(inactiveThingState).toBe(ThingState.Offline)
  })

  it('check unknown state', async () => {
    const store = initValue()

    const unknownThing = await store.getThing(new Id(thing3Id))
    const unknownThingState = await unknownThing.isActive()
    expect(unknownThingState).toBe(ThingState.Unknown)
  })

  it('get datastreams sensor 1', async () => {
    const store = initValue()

    const thing1 = await store.getThing(new Id(thing1Id))
    const datastreams1 = await thing1.getDatastreams()
    datastreamCollectionMatches(datastreams1, thing1Datastreams)
  })

  it('get datastreams sensor 2', async () => {
    const store = initValue()

    const thing2 = await store.getThing(new Id(thing2Id))
    const datastreams2 = await thing2.getDatastreams()
    datastreamCollectionMatches(datastreams2, thing2Datastreams)
  })

  it('get datastreams sensor 3', async () => {
    const store = initValue()

    const thing3 = await store.getThing(new Id(thing3Id))
    const datastreams3 = await thing3.getDatastreams()
    datastreamCollectionMatches(datastreams3, thing3Datastreams)
  })
})
