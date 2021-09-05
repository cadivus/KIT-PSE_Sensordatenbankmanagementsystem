import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayView from './ReplayView'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import {sensor1, sensor2, sensor3} from '../../test/mock/store/communication/mockData/backend/getJson'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const selectedThings = {sensor1, sensor2, sensor3}
  const {getByTestId} = renderWithProviders(<ReplayView state={selectedThings} />)

  const replayViewContainer = getByTestId(/replayView-container/)
  expect(replayViewContainer).toBeInTheDocument()
})
