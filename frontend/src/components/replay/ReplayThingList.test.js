import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import ReplayThingList from './ReplayThingList'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import ThingStore from '../../store/ThingStore'
import DatastreamStore from '../../store/DatastreamStore'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const allThings = await thingStore.things

  const {getByTestId} = renderWithProviders(<ReplayThingList things={new Set(allThings)} />)

  const valueText = getByTestId(/value-text/)
  const thingText = getByTestId(/thing-text/)
  expect(valueText).toBeInTheDocument()
  expect(thingText).toBeInTheDocument()
})
