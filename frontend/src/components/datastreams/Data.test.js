import React from 'react'
import {waitFor} from '@testing-library/react'
import {renderWithProviders} from '../../../test/jestHelper/customRender'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../../test/mock/store/communication/restClientMock'
import {datastreamThing1Id} from '../../../test/mock/store/communication/mockData/backend/getJson'
import DatastreamStore from '../../store/DatastreamStore'
import Data from './Data'
import Id from '../../types/Id'

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

test('check for data', async () => {
  const datastream = await getDatastream(datastreamThing1Id)
  const {container, getAllByTestId} = renderWithProviders(<Data datastream={datastream} />)

  // Loading detection
  await waitFor(() => {
    expect(getAllByTestId(/tableRow/)[0]).toBeInTheDocument()
  })

  const values = await datastream.getAllValues(25)
  values.forEach(row => {
    expect(container.innerHTML.includes(row.value.valueToString())).toBe(true)
    expect(container.innerHTML.includes(row.value.unit.toString())).toBe(true)
    expect(container.innerHTML.includes(row.date.getDay())).toBe(true)
    expect(container.innerHTML.includes(row.date.getHours())).toBe(true)
    expect(container.innerHTML.includes(row.date.getMinutes())).toBe(true)
  })
})
