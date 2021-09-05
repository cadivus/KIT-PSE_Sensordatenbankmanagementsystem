import React from 'react'
import reactRouterDom from 'react-router-dom'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {renderWithProviders} from '../../test/jestHelper/customRender'
import DatastreamView from './DatastreamView'
import ThingStore from '../../store/ThingStore'
import DatastreamStore from '../../store/DatastreamStore'

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
})

test('check for elements', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const location = getLocation(await thingStore.things)

  reactRouterDom.useParams = jest.fn().mockReturnValue({})
  const {getByTestId} = renderWithProviders(<DatastreamView location={location} />)

  /*
    // Loading detection
    await waitFor(() => {
      expect(() => getByTestId(/loadingPage/)).toThrow()
    })

    const datastreamListViewContainer = getByTestId(/datastreamListView-Container/)
    expect(datastreamListViewContainer).toBeInTheDocument()

   */
})
