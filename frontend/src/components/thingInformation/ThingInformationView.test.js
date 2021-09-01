import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Properties from './Properties'
import ThingStore from '../../store/ThingStore'
import {sensor1, sensor1Id} from '../../test/mock/store/communication/mockData/backend/getJson'
import DatastreamStore from '../../store/DatastreamStore'
import ThingInformationView from "./ThingInformationView";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for informations', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const thing = await thingStore.getThing(sensor1Id)

  const wrapper = mount(<Properties thing={thing}>
    <ThingInformationView/>
  </Properties>)

  expect(wrapper.html().includes("Description: ")).toBe(true)
  expect(wrapper.html().includes("Location: ")).toBe(true)
  expect(wrapper.html().includes("shortname: ")).toBe(true)
  expect(wrapper.html().includes("hardware.id: ")).toBe(true)
  expect(wrapper.html().includes("operator.domain: ")).toBe(true)
  expect(wrapper.html().includes("Street:")).toBe(true)
  expect(wrapper.html().includes("City:")).toBe(true)
  expect(wrapper.html().includes("Coordinates:")).toBe(true)

})
