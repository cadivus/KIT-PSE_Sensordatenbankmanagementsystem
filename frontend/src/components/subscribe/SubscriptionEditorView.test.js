import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import SubscriptionEditorView from './SubscriptionEditorView'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<SubscriptionEditorView />)

  const subscribeChangeButton = getByTestId(/subscribe-change-button/)
  const thingTable = getByTestId(/thing-table/)
  expect(subscribeChangeButton).toBeInTheDocument()
  expect(thingTable).toBeInTheDocument()
})
