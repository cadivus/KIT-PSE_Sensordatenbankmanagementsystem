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
  it('get data length', async () => {
    const value = initValue()

    const fetchedThings = await value.things
    expect(fetchedThings.length).toBe(3)
  })
})
