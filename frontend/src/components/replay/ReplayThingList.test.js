import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayThingList from './ReplayThingList'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import {sensor1, sensor2, sensor3} from "../../test/mock/store/communication/mockData/backend/getJson";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(
    <ReplayThingList things={{sensor1, sensor2, sensor3}} />,
  )

  const valueText = getByTestId(/value-text/)
  const thingText = getByTestId(/thing-text/)
  expect(valueText).toBeInTheDocument()
  expect(thingText).toBeInTheDocument()
})
