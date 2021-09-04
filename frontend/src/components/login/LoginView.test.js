import React from 'react'
import {fireEvent, render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import LoginView from './LoginView'
import {email1} from '../../test/mock/store/communication/mockData/notification/getText'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements of step 1', async () => {
  const wrapper = ({children}) => (
    <Providers>
      {children}
    </Providers>
  )
  const {getByTestId} = render(<LoginView />, {wrapper})

  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  expect(firstButton).toBeInTheDocument()
  expect(emailField).toBeInTheDocument()
})

test('check for elements of step 2', async () => {
  const wrapper = ({children}) => (
    <Providers>
      {children}
    </Providers>
  )
  const {getByTestId} = render(<LoginView />, {wrapper})

  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  expect(firstButton).toBeInTheDocument()
  expect(emailField).toBeInTheDocument()

  emailField.value = email1.toString()
  fireEvent.input(emailField, {target: {value: email1.toString()}})
  firstButton.click()

  // A bit hacky, but gives enough time for the mock to send the confirmation code
  await new Promise(r => setTimeout(r, 1000))

  const secondButton = getByTestId(/second-login-button/)
  expect(secondButton).toBeInTheDocument()
})

test('test login and logout', async () => {
  const wrapper = ({children}) => (
    <Providers>
      {children}
    </Providers>
  )
  const {getByTestId} = render(<LoginView />, {wrapper})

  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  expect(firstButton).toBeInTheDocument()
  expect(emailField).toBeInTheDocument()

  emailField.value = email1.toString()
  fireEvent.input(emailField, {target: {value: email1.toString()}})
  firstButton.click()

  // A bit hacky, but gives enough time for the mock to send the confirmation code
  await new Promise(r => setTimeout(r, 1000))

  const secondButton = getByTestId(/second-login-button/)
  const codeField = getByTestId(/login-code-field/)
  expect(secondButton).toBeInTheDocument()
  codeField.value = email1.toString()
  fireEvent.input(codeField, {target: {value: email1.toString()}})
  secondButton.click()
})
