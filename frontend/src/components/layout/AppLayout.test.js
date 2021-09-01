import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import AppLayout from "./AppLayout";
import {sensor1} from "../../test/mock/store/communication/mockData/backend/getJson";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for things', async () => {
  const wrapper = mount(<Providers><AppLayout /></Providers>)

  expect(wrapper.html().includes("login")).toBe(true)
  expect(wrapper.html().includes("Deutsch")).toBe(true)
  expect(wrapper.html().includes("SUBSCRIPTIONS")).toBe(true)

})
