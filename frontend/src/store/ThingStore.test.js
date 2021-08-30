import ThingStore from './ThingStore'
import DatastreamStore from './DatastreamStore'
import {getJson} from './communication/restClient'
import {getJson as getJsonMock} from '../test/mock/store/communication/restClientMock'

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
})

describe('cache', () => {
  it('check cache length', async () => {
    const store = initValue()

    await store.things
    const cachedThings = store.cachedThings
    expect(cachedThings.length).toBe(3)
  })
})
