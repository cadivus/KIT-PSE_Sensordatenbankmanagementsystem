import React from 'react'
import {waitFor} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import AppLayout from './AppLayout'
import UserStore from '../../store/UserStore'
import {confirm1, email1} from '../../test/mock/store/communication/mockData/notification/getText'
import {renderWithProviders} from '../../test/jestHelper/customRender'

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = renderWithProviders(<AppLayout />)

  const homeButton = getByTestId(/home-button/)
  const subscriptionField = getByTestId(/subscription-button/)
  const languageButton = getByTestId(/language-button/)
  const loginButton = getByTestId(/login-button/)
  expect(homeButton).toBeInTheDocument()
  expect(subscriptionField).toBeInTheDocument()
  expect(languageButton).toBeInTheDocument()
  expect(loginButton).toBeInTheDocument()
})

test('change language', async () => {
  const {getByTestId} = renderWithProviders(<AppLayout />)

  const languageButton = getByTestId(/language-button/)
  const subscriptionButton = getByTestId(/subscription-button/)
  const loginButton = getByTestId(/login-button/)
  expect(languageButton).toBeInTheDocument()
  expect(subscriptionButton).toBeInTheDocument()
  expect(loginButton).toBeInTheDocument()

  expect(languageButton.innerHTML.includes('Deutsch')).toBe(true)
  expect(subscriptionButton.innerHTML.includes('SUBSCRIPTIONS')).toBe(true)
  expect(loginButton.innerHTML.includes('LOGIN')).toBe(true)

  languageButton.click()
  expect(languageButton.innerHTML.includes('English')).toBe(true)
  expect(subscriptionButton.innerHTML.includes('Abonnements')).toBe(true)
  expect(loginButton.innerHTML.includes('Einloggen')).toBe(true)

  languageButton.click()
  expect(languageButton.innerHTML.includes('Deutsch')).toBe(true)
  expect(subscriptionButton.innerHTML.includes('SUBSCRIPTIONS')).toBe(true)
  expect(loginButton.innerHTML.includes('LOGIN')).toBe(true)
})
