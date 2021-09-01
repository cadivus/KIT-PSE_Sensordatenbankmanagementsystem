import React from 'react'
import reactRouterDom from 'react-router-dom'
import {render, waitFor} from '@testing-library/react'
import {mount} from 'enzyme'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {sensor1, sensor1Id} from '../../test/mock/store/communication/mockData/backend/getJson'
import Providers from '../Providers'
import ThingInformationView from './ThingInformationView'
import Properties from './Properties'
import ThingStore from '../../store/ThingStore'
import DatastreamStore from '../../store/DatastreamStore'

jest.mock('../../store/communication/restClient')
jest.mock('react-router-dom')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const pushMock = jest.fn()
  reactRouterDom.useHistory = jest.fn().mockReturnValue({push: pushMock})
})

describe('test with thing 1', () => {
  beforeEach(() => {
    reactRouterDom.useParams = jest.fn().mockReturnValue({thingId: sensor1Id})
  })

  test('check for description', async () => {
    const {container, getByTestId} = render(<Providers><ThingInformationView /></Providers>)

    // Loading detection
    await waitFor(() => {
      expect(() => getByTestId(/loadingPage/)).toThrow()
    })

    expect(container.innerHTML.includes(sensor1.description)).toBe(true)
  })
})

test('check for information', async () => {
  const thingStore = new ThingStore(new DatastreamStore())
  const thing = await thingStore.getThing(sensor1Id)

  const wrapper = mount(<Properties thing={thing} />)

  expect(wrapper.html().includes("Description: ")).toBe(true)
  expect(wrapper.html().includes("Location: ")).toBe(true)
  expect(wrapper.html().includes("shortname: ")).toBe(true)
  expect(wrapper.html().includes("hardware.id: ")).toBe(true)
  expect(wrapper.html().includes("operator.domain: ")).toBe(true)
  expect(wrapper.html().includes("Street:")).toBe(true)
  expect(wrapper.html().includes("City:")).toBe(true)
  expect(wrapper.html().includes("Coordinates:")).toBe(true)

})
