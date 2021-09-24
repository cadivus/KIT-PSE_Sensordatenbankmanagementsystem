import React from 'react'
import reactRouterDom from 'react-router-dom'
import {waitFor} from '@testing-library/react'
import {renderWithProviders} from '../../../test/jestHelper/customRender'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../../test/mock/store/communication/restClientMock'
import DatastreamView from './DatastreamView'
import {datastreamThing1Id, thing1, thing1Id} from '../../../test/mock/store/communication/mockData/backend/getJson'
import DatastreamStore from '../../store/DatastreamStore'
import Id from '../../types/Id'

jest.mock('../../store/communication/restClient')
jest.mock('react-router-dom')

const getDatastream = datastreamId => {
  const datastreamStore = new DatastreamStore()
  return datastreamStore.getDatastream(new Id(datastreamId))
}

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  reactRouterDom.useParams = jest.fn().mockReturnValue({thingId: thing1Id, datastreamId: datastreamThing1Id})
  const {getByTestId} = renderWithProviders(<DatastreamView />)

  // Loading detection
  await waitFor(() => {
    expect(() => getByTestId(/loadingPage/)).toThrow()
  })

  const datastreamListViewContainer = getByTestId(/datastreamDataTableContainer/)
  expect(datastreamListViewContainer).toBeInTheDocument()

  const exportPanel = getByTestId(/exportPanel/)
  expect(exportPanel).toBeInTheDocument()

  const propertiesPanel = getByTestId(/thingPropertiesPanel/)
  expect(propertiesPanel).toBeInTheDocument()

  const thingTitle = getByTestId(/thingTitle/)
  expect(thingTitle).toBeInTheDocument()
})

test('check for data', async () => {
  const datastream = await getDatastream(datastreamThing1Id)

  reactRouterDom.useParams = jest.fn().mockReturnValue({thingId: thing1Id, datastreamId: datastreamThing1Id})
  const {getByTestId} = renderWithProviders(<DatastreamView />)

  // Loading detection
  await waitFor(() => {
    expect(() => getByTestId(/loadingPage/)).toThrow()
  })

  const datastreamListViewContainer = getByTestId(/datastreamDataTableContainer/)

  // Check whether data have been loaded
  const values = await datastream.getAllValues(25)
  values.forEach(row => {
    expect(datastreamListViewContainer.innerHTML.includes(row.value.valueToString())).toBe(true)
    expect(datastreamListViewContainer.innerHTML.includes(row.value.unit.toString())).toBe(true)
    expect(datastreamListViewContainer.innerHTML.includes(row.date.getDay())).toBe(true)
    expect(datastreamListViewContainer.innerHTML.includes(row.date.getHours())).toBe(true)
    expect(datastreamListViewContainer.innerHTML.includes(row.date.getMinutes())).toBe(true)
  })

  // Check whether thing title has been loaded
  const thingTitle = getByTestId(/thingTitle/)
  expect(thingTitle.innerHTML.includes(thing1.name)).toBe(true)
})
