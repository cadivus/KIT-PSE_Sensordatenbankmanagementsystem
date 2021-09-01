import React from 'react'
import {mount} from 'enzyme'
import {waitFor} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import StartpageView from "./StartpageView";
import {sensor1, sensor2, sensor3} from '../../test/mock/store/communication/mockData/backend/getJson'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for things', async () => {
  const wrapper = mount(
    <Providers>
      <StartpageView />
    </Providers>,
  )

  expect(wrapper.html().includes("Startpage")).toBe(true)
  expect(wrapper.html().includes("Subscribe")).toBe(true)
  expect(wrapper.html().includes("Replay")).toBe(true)
  expect(wrapper.html().includes("Thing")).toBe(true)
})
