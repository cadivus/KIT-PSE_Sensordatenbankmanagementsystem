import DatastreamStore from './DatastreamStore'
import {getJson, getText, postJsonGetText} from './communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../test/mock/store/communication/restClientMock'
import {
  datastreamThing1Id,
  datastreamThing2Id,
  datastreamThing3Id,
  thing1Datastream1,
  thing1Datastreams,
  thing1Id,
  thing2Datastream1,
  thing2Datastreams,
  thing2Id,
  thing3Datastream1,
  thing3Datastreams,
  thing3Id,
} from '../test/mock/store/communication/mockData/backend/getJson'
import Id from '../types/Id'
import datastreamMatches, {datastreamCollectionMatches} from '../test/matchTest/material/DatastreamMatch'
import {datastreamRowListValuesMatches} from '../test/matchTest/material/DatastreamRowMatch'
import {thing1Datastream1Json} from '../test/mock/store/communication/mockData/backend/files/csvFiles'

jest.mock('./communication/restClient')

const initValue = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const datastreamStore = new DatastreamStore()

  return datastreamStore
}

describe('get datastreams by id', () => {
  it('get datastreams of first thing', async () => {
    const store = initValue()

    const datastream1 = await store.getDatastream(new Id(datastreamThing1Id))
    datastreamMatches(datastream1, thing1Datastream1)
  })

  it('get datastreams of second thing', async () => {
    const store = initValue()

    const datastream2 = await store.getDatastream(new Id(datastreamThing2Id))
    datastreamMatches(datastream2, thing2Datastream1)
  })

  it('get datastreams of third thing', async () => {
    const store = initValue()

    const datastream3 = await store.getDatastream(new Id(datastreamThing3Id))
    datastreamMatches(datastream3, thing3Datastream1)
  })
})

describe('get datastreams for thing', () => {
  it('get datastreams of first thing', async () => {
    const store = initValue()

    const datastreams1 = await store.getDatastreams(new Id(thing1Id))
    datastreamCollectionMatches(datastreams1, thing1Datastreams)
  })

  it('get datastreams of second thing', async () => {
    const store = initValue()

    const datastreams2 = await store.getDatastreams(new Id(thing2Id))
    datastreamCollectionMatches(datastreams2, thing2Datastreams)
  })

  it('get datastreams of third thing', async () => {
    const store = initValue()

    const datastreams3 = await store.getDatastreams(new Id(thing3Id))
    datastreamCollectionMatches(datastreams3, thing3Datastreams)
  })
})

describe('test datastream implementation', () => {
  it('get datastreams of first thing', async () => {
    const store = initValue()

    const datastream1 = await store.getDatastream(new Id(datastreamThing1Id))
    const datastream1Values = await datastream1.getAllValues(25)
    datastreamRowListValuesMatches(datastream1Values, thing1Datastream1Json)
  })

  it('get datastreams of first thing two times', async () => {
    const store = initValue()

    await store.getDatastream(new Id(datastreamThing1Id))
    const datastream1 = await store.getDatastream(new Id(datastreamThing1Id))
    const datastream1Values = await datastream1.getAllValues(25)
    datastreamRowListValuesMatches(datastream1Values, thing1Datastream1Json)
  })
})
