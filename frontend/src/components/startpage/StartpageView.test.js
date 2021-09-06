import React from 'react'
import {fireEvent, render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import StartpageView from './StartpageView'
import {renderWithProviders} from '../../test/jestHelper/customRender'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<StartpageView />)

  const startpageText = getByTestId(/startpage-text/)
  const subscribeButton = getByTestId(/subscribe-button/)
  const replayButton = getByTestId(/replay-button/)
  expect(startpageText).toBeInTheDocument()
  expect(subscribeButton).toBeInTheDocument()
  expect(replayButton).toBeInTheDocument()
})

test('test search', async () => {
  const {getByTestId} = renderWithProviders(<StartpageView />)

  const startpageText = getByTestId(/startpage-text/)
  const subscribeButton = getByTestId(/subscribe-button/)
  const replayButton = getByTestId(/replay-button/)
  const searchBar = getByTestId(/searchBar/)
  expect(startpageText).toBeInTheDocument()
  expect(subscribeButton).toBeInTheDocument()
  expect(replayButton).toBeInTheDocument()
  expect(searchBar).toBeInTheDocument()

  fireEvent.input(searchBar, 'Node')
  fireEvent.input(searchBar, KeyboardEvent.ENTER)
})
