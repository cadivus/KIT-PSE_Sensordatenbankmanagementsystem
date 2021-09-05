import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayHelp from './ReplayHelp'
import {renderWithProviders} from '../../test/jestHelper/customRender'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<ReplayHelp />)

  const helpButton = getByTestId(/help-button/)
  expect(helpButton).toBeInTheDocument()
})

test('checking if the Message is displayed', async () => {
  const {getByTestId} = renderWithProviders(<ReplayHelp />)

  const helpButton = getByTestId(/help-button/)
  expect(helpButton).toBeInTheDocument()

  helpButton.click()

  const helpMessage = getByTestId(/help-message/)
  expect(helpMessage).toBeInTheDocument()
})
