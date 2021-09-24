import React from 'react'
import {fireEvent} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../../test/mock/store/communication/restClientMock'
import ReplaySettings from './ReplaySettings'
import {renderWithProviders} from '../../../test/jestHelper/customRender'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<ReplaySettings />)

  const startText = getByTestId(/start-text/)
  const startDateField = getByTestId(/startDate-field/)
  const stopText = getByTestId(/stop-text/)
  const stopDateField = getByTestId(/stopDate-field/)
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

  const startDateField = getByTestId(/startDate-field/)
  const stopDateField = getByTestId(/stopDate-field/)
  expect(startDateField).toBeInTheDocument()
  expect(stopDateField).toBeInTheDocument()

  fireEvent.input(startDateField, {target: {value: '2021-01-01T10:00'}})
  fireEvent.input(stopDateField, {target: {value: '2021-01-01T10:00'}})
})

test('start replay', async () => {
  const {getByTestId} = renderWithProviders(<ReplaySettings />)

  const playButton = getByTestId(/play-button/)
  expect(playButton).toBeInTheDocument()

  playButton.click()
})
