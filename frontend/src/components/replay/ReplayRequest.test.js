import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayRequest from './ReplayRequest'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import Replay from '../../material/Replay'
import {
  sensor1,
  sensor1Datastream1End,
  sensor1Datastream1Start,
} from '../../test/mock/store/communication/mockData/backend/getJson'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements with valid replay', async () => {
  const replay = new Replay(sensor1.id, sensor1Datastream1Start, sensor1Datastream1End, 10, {sensor1})
  const {getByTestId} = renderWithProviders(<ReplayRequest replay={replay} />)

  const replayText = getByTestId(/replay-text/)
  expect(replayText).toBeInTheDocument()
})

// there are no elements which could be tested
test('check for elements with invalid replay', async () => {
  renderWithProviders(<ReplayRequest replay={null} />)
})
