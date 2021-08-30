import DatastreamStore from './DatastreamStore'
import {getJson, getText} from './communication/restClient'
import {getJson as getJsonMock, getText as getTextMock} from '../test/mock/store/communication/restClientMock'
import {
  datastreamSensor1Id,
  datastreamSensor2Id,
  datastreamSensor3Id,
  sensor1Datastream1,
  sensor1Datastreams, sensor1Id,
  sensor2Datastream1,
  sensor2Datastreams, sensor2Id,
  sensor3Datastream1,
  sensor3Datastreams, sensor3Id,
} from '../test/mock/store/communication/mockData/backend/getJson'
import Id from '../material/Id'
import datastreamMatches, {datastreamCollectionMatches} from '../test/matchTest/material/DatastreamMatch'

jest.mock('./communication/restClient')

const initValue = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)

  const datastreamStore = new DatastreamStore()

  return datastreamStore
}

describe('get datastreams by id', () => {
  it('get datastreams of first thing', async () => {
    const store = initValue()

    const datastream1 = await store.getDatastream(new Id(datastreamSensor1Id))
    datastreamMatches(datastream1, sensor1Datastream1)
  })

  it('get datastreams of second thing', async () => {
    const store = initValue()

    const datastream2 = await store.getDatastream(new Id(datastreamSensor2Id))
    datastreamMatches(datastream2, sensor2Datastream1)
  })

  it('get datastreams of third thing', async () => {
    const store = initValue()

    const datastream3 = await store.getDatastream(new Id(datastreamSensor3Id))
    datastreamMatches(datastream3, sensor3Datastream1)
  })
})

describe('get datastreams for thing', () => {
  it('get datastreams of first sensor', async () => {
    const store = initValue()

    const datastreams1 = await store.getDatastreams(new Id(sensor1Id))
    datastreamCollectionMatches(datastreams1, sensor1Datastreams)
  })

  it('get datastreams of second sensor', async () => {
    const store = initValue()

    const datastreams2 = await store.getDatastreams(new Id(sensor2Id))
    datastreamCollectionMatches(datastreams2, sensor2Datastreams)
  })

  it('get datastreams of third sensor', async () => {
    const store = initValue()

    const datastreams3 = await store.getDatastreams(new Id(sensor3Id))
    datastreamCollectionMatches(datastreams3, sensor3Datastreams)
  })
})
