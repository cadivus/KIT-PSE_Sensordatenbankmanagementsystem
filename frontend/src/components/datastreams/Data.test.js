import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Properties from '../thingInformation/Properties'
import ThingStore from '../../store/ThingStore'
import {datastreamSensor1Id, sensor1Datastream1} from "../../test/mock/store/communication/mockData/backend/getJson";
import {sensor1, sensor1Id} from "../../test/mock/store/communication/mockData/backend/getJson";
import DatastreamStore from '../../store/DatastreamStore'
import Data from './Data'
import Providers from "../Providers";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for informations', async () => {
  const datastreamStore = new DatastreamStore()
  const thingStore = new ThingStore(datastreamStore)
  const thing = await thingStore.getThing(sensor1Id)
  const datastream = await datastreamStore.getDatastream(datastreamSensor1Id)

  const wrapper = mount(<Providers>
    <Data datastream={datastream}/></Providers>)

  expect(wrapper.html().includes("Time")).toBe(true)
  expect(wrapper.html().includes("Data")).toBe(true)


})
