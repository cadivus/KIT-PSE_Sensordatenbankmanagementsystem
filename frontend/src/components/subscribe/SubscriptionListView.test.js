import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import SubscriptionListView from './SubscriptionListView'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const props = {
    history: {
      length: 4,
      action: 'PUSH',
      location: {
        pathname: '/subscriptions/subscriptionCreate',
        state: {
          selectedThings: {},
        },
        search: '',
        hash: '',
        key: 'r6c784',
      },
    },
    location: {
      pathname: '/subscriptions/subscriptionCreate',
      state: {
        selectedThings: {},
      },
      search: '',
      hash: '',
      key: 'r6c784',
    },
    match: {
      path: '/subscriptions/subscriptionCreate',
      url: '/subscriptions/subscriptionCreate',
      isExact: true,
      params: {},
    },
  }

  const {getByTestId} = renderWithProviders(<SubscriptionListView props={props} />)

  const subscriptionListViewContainer = getByTestId(/subscriptionListView-Container/)
  expect(subscriptionListViewContainer).toBeInTheDocument()
})
