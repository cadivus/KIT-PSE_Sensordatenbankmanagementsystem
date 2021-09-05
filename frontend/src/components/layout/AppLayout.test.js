import React from 'react'
import {render, wait, waitFor} from '@testing-library/react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
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
  const {container, getByTestId} = renderWithProviders(<AppLayout />)

  const languageButton = getByTestId(/language-button/)
  expect(languageButton).toBeInTheDocument()
  expect(container.innerHTML.includes('Deutsch')).toBe(true)
  expect(container.innerHTML.includes('SUBSCRIPTIONS')).toBe(true)
  expect(container.innerHTML.includes('login')).toBe(true)
  languageButton.click()
  expect(container.innerHTML.includes('English')).toBe(true)
  expect(container.innerHTML.includes('Abonnements')).toBe(true)
  expect(container.innerHTML.includes('Einloggen')).toBe(true)
  languageButton.click()
  expect(container.innerHTML.includes('Deutsch')).toBe(true)
  expect(container.innerHTML.includes('SUBSCRIPTIONS')).toBe(true)
  expect(container.innerHTML.includes('login')).toBe(true)
})

test('logout', async () => {
  const {getByTestId} = renderWithProviders(<AppLayout />)

  const userStore = new UserStore()
  await waitFor(() => {
    console.log(userStore.requestUser(email1.toString(), confirm1))
  })

  const loginButton = getByTestId(/login-button/)
  expect(loginButton).toBeInTheDocument()
})
