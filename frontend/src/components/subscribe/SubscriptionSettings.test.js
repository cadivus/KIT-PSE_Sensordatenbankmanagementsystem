import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import SubscriptionSettings from './SubscriptionSettings'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<SubscriptionSettings directNotification={false} notificationLevel={5} />)

  const directNotificationRow = getByTestId(/directNotification-Row/)
  const protocolRow = getByTestId(/protocol-Row/)
  expect(directNotificationRow).toBeInTheDocument()
  expect(protocolRow).toBeInTheDocument()
})
