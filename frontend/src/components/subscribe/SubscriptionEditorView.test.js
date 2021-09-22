import React from 'react'
import reactRouterDom from 'react-router-dom'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import SubscriptionEditorView from './SubscriptionEditorView'
import DatastreamStore from '../../store/DatastreamStore'
import ThingStore from '../../store/ThingStore'

jest.mock('../../store/communication/restClient')
jest.mock('react-router-dom')

const getLocation = (selectedSensors = []) => {
  const location = {
    pathname: '/subscriptions/subscriptionCreate',
    state: {
      selectedThings: new Set(selectedSensors),
    },
    search: '',
    hash: '',
    key: 'r6c784',
  }
  return location
}

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const pushMock = jest.fn()
  reactRouterDom.useHistory = jest.fn().mockReturnValue({push: pushMock})
  reactRouterDom.Redirect = jest.fn().mockReturnValue(<div data-testid="replaces-redirect" />)
})

test('check for elements on creating a new subscription with multiple things', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const location = getLocation(await thingStore.things)

  reactRouterDom.useParams = jest.fn().mockReturnValue({})
  const {getByTestId} = renderWithProviders(<SubscriptionEditorView location={location} />)

  const subscribeChangeButton = getByTestId(/subscribe-change-button/)
  const thingTable = getByTestId(/thing-table/)
  expect(subscribeChangeButton).toBeInTheDocument()
  expect(thingTable).toBeInTheDocument()
})
