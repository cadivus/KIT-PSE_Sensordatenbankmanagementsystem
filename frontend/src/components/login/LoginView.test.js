import React from 'react'
import reactRouterDom from 'react-router-dom'
import {fireEvent, render} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import LoginView from './LoginView'
import {confirm1, email1} from '../../test/mock/store/communication/mockData/notification/getText'

jest.mock('../../store/communication/restClient')
jest.mock('react-router-dom')

const customRender = ui => {
  const wrapper = ({children}) => (
    <Providers>
      {children}
    </Providers>
  )
  return render(ui, {wrapper})
}

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const pushMock = jest.fn()
  reactRouterDom.useHistory = jest.fn().mockReturnValue({push: pushMock})
})

test('check for elements of step 1', async () => {
  const {getByTestId} = customRender(<LoginView />)

  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  expect(firstButton).toBeInTheDocument()
  expect(emailField).toBeInTheDocument()
})

test('check for elements of step 2', async () => {
  const {getByTestId} = customRender(<LoginView />)

  // Simulate step 1
  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  emailField.value = email1.toString()
  fireEvent.input(emailField, {target: {value: email1.toString()}})
  firstButton.click()

  // A bit hacky, but gives enough time for the mock to send the confirmation code
  await new Promise(r => setTimeout(r, 300))

  const secondButton = getByTestId(/second-login-button/)
  const codeField = getByTestId(/login-code-field/)
  expect(secondButton).toBeInTheDocument()
  expect(codeField).toBeInTheDocument()
})

test('test login and logout', async () => {
  const {getByTestId} = customRender(<LoginView />)

  expect(getByTestId(/not-logged-in/)).toBeInTheDocument()

  // Simulate step 1
  const firstButton = getByTestId(/first-login-button/)
  const emailField = getByTestId(/login-email-field/)
  emailField.value = email1.toString()
  fireEvent.input(emailField, {target: {value: email1.toString()}})
  firstButton.click()

  // A bit hacky, but gives enough time for the mock to send the confirmation code
  await new Promise(r => setTimeout(r, 300))

  // Simulate step 2
  const secondButton = getByTestId(/second-login-button/)
  const codeField = getByTestId(/login-code-field/)
  emailField.value = confirm1
  fireEvent.input(codeField, {target: {value: confirm1}})
  secondButton.click()

  expect(getByTestId(/logged-in/)).toBeInTheDocument()
})
