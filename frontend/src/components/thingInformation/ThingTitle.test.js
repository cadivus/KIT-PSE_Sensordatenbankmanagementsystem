import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../../test/mock/store/communication/restClientMock'
import Properties from './Properties'
import ThingStore from '../../store/ThingStore'
import {thing1, thing1Id} from '../../../test/mock/store/communication/mockData/backend/getJson'
import DatastreamStore from '../../store/DatastreamStore'
import ThingTitle from './ThingTitle'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for sensors', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const thing = await thingStore.getThing(thing1Id)

  const wrapper = mount(<ThingTitle thing={thing} />)

  expect(wrapper.html().includes(thing1.name)).toBe(true)
})
