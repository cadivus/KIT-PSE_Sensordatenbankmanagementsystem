import React from 'react'
import {fireEvent} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplaySettings from './ReplaySettings'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import {email1} from '../../test/mock/store/communication/mockData/notification/getText'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<ReplaySettings />)

  const startText = getByTestId(/start-text/)
  const startDateField = getByTestId(/startDate-Field/)
  const stopText = getByTestId(/stop-text/)
  const stopDateField = getByTestId(/stopDate-Field/)
  const speedText = getByTestId(/speed-text/)
  const speedValue = getByTestId(/speed-value/)
  const playButton = getByTestId(/play-button/)
  expect(startText).toBeInTheDocument()
  expect(startDateField).toBeInTheDocument()
  expect(stopText).toBeInTheDocument()
  expect(stopDateField).toBeInTheDocument()
  expect(speedText).toBeInTheDocument()
  expect(speedValue).toBeInTheDocument()
  expect(playButton).toBeInTheDocument()
})

test('change settings', async () => {
  const {getByTestId} = renderWithProviders(<ReplaySettings />)

  const startDateField = getByTestId(/startDate-Field/)
  const stopDateField = getByTestId(/stopDate-Field/)
  const speedValue = getByTestId(/speed-value/)
  expect(startDateField).toBeInTheDocument()
  expect(stopDateField).toBeInTheDocument()
  expect(speedValue).toBeInTheDocument()

  fireEvent.input(startDateField, '2020-01-01T10:00')
  fireEvent.input(stopDateField, '2020-02-02T10:00')
  fireEvent.input(speedValue, '50')
})

test('start replay', async () => {
  const {getByTestId} = renderWithProviders(<ReplaySettings />)

  const playButton = getByTestId(/play-button/)
  expect(playButton).toBeInTheDocument()

  playButton.click()
})
