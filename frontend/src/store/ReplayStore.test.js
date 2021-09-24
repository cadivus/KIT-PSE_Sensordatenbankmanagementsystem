import {getJson, getText, postJsonGetText} from './communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayStore from './ReplayStore'
import ThingStore from './ThingStore'
import ReplaySpeed from '../types/ReplaySpeed'
import {thing1Id} from '../../test/mock/store/communication/mockData/backend/getJson'
import Id from '../types/Id'
import DatastreamStore from './DatastreamStore'
import {thing1Replay} from '../../test/mock/store/communication/mockData/backend/postJsonGetText'

jest.mock('./communication/restClient')

const initStore = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const replayStore = new ReplayStore()
  return replayStore
}

const getThingStore = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const thingStore = new ThingStore(new DatastreamStore())
  return thingStore
}

test('test creating replay', async () => {
  const store = initStore()
  const thingsStore = getThingStore()

  const startDate = new Date(1567214040)
  const endDate = new Date(1630372440)
  const replaySpeed = new ReplaySpeed(3)

  const thing = await thingsStore.getThing(new Id(thing1Id))
  const things = new Set([thing])

  const replay = await store.createReplay(startDate, endDate, replaySpeed, things)

  expect(replay.id.toString()).toBe(thing1Replay)
})
