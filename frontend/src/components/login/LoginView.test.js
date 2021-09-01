import React from 'react'
import {render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import LoginView from './LoginView'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements of step 1', async () => {
  const {getByTestId} = render(
    <Providers>
      <LoginView />
    </Providers>
  )

  expect(getByTestId(/first-login-button/)).toBeInTheDocument()
  expect(getByTestId(/login-email-field/)).toBeInTheDocument()
})
