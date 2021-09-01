import React from 'react'
import reactRouterDom from 'react-router-dom'
import {render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import {sensor1, sensor1Id} from '../../test/mock/store/communication/mockData/backend/getJson'
import Providers from '../Providers'
import ThingInformationView from './ThingInformationView'
import {waitFor} from '@testing-library/react'

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
