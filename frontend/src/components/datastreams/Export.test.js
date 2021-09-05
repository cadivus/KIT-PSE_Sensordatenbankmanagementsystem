import React from 'react'
import {fireEvent, render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {datastreamSensor1Id} from '../../test/mock/store/communication/mockData/backend/getJson'

import DatastreamStore from '../../store/DatastreamStore'
import Export from './Export'
import Providers from '../Providers'
import Id from '../../material/Id'
import {renderWithProviders} from "../../test/jestHelper/customRender";
import Data from "./Data";

jest.mock('../../store/communication/restClient')

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
  const datastream = await getDatastream(datastreamSensor1Id)

  const {getByTestId} = renderWithProviders(<Export datastream={datastream} />)

  const exportButton = getByTestId(/export-button/)
  const startField = getByTestId(/start-field/)
  const endField = getByTestId(/end-field/)
  expect(exportButton).toBeInTheDocument()
  expect(startField).toBeInTheDocument()
  expect(endField).toBeInTheDocument()
})

test('change settings', async () => {
  const datastream = await getDatastream(datastreamSensor1Id)

  const {getByTestId} = renderWithProviders(<Export datastream={datastream} />)

  const startField = getByTestId(/start-field/)
  const endField = getByTestId(/end-field/)
  expect(startField).toBeInTheDocument()
  expect(endField).toBeInTheDocument()

  fireEvent.input(startField, '2021-01-01T10:00')
  fireEvent.input(endField, '2021-02-01T10:00')
})
