import React from 'react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import ThingList from './ThingList'
import {waitFor} from '@testing-library/react'
import {Checkbox} from '@material-ui/core'
import {sensor1, sensor2, sensor3} from '../../test/mock/store/communication/mockData/backend/getJson'

jest.mock('../../store/communication/restClient')
getJson.mockImplementation(getJsonMock)
getText.mockImplementation(getTextMock)
postJsonGetText.mockImplementation(postJsonGetTextMock)

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for sensors', async () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
  const wrapper = mount(<Providers><ThingList selectedThings={new Set()} searchExpression={new RegExp('^.*$', 'i')} /></Providers>)

  // Loading detection
  await waitFor(() => {
    wrapper.containsMatchingElement(<Checkbox />)
  })

  expect(wrapper.html().includes(sensor1.name)).toBe(true)
  expect(wrapper.html().includes(sensor2.name)).toBe(true)
  expect(wrapper.html().includes(sensor3.name)).toBe(true)
})
