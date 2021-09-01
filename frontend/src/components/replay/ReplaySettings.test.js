import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import ReplaySettings from "./ReplaySettings";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for things', async () => {
  const wrapper = mount(<Providers><ReplaySettings /></Providers>)

  expect(wrapper.html().includes("Start:")).toBe(true)
  expect(wrapper.html().includes("Stop:")).toBe(true)
  expect(wrapper.html().includes("Speed:")).toBe(true)

})
