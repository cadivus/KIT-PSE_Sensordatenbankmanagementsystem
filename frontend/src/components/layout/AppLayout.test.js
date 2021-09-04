import React from 'react'
import {getJson, getText, postJsonGetText} from '../../store/communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../../test/mock/store/communication/restClientMock'
import Providers from '../Providers'
import AppLayout from './AppLayout'
import {render, wait, waitFor} from "@testing-library/react";
import UserStore from "../../store/UserStore";
import {confirm1, email1} from "../../test/mock/store/communication/mockData/notification/getText";

jest.mock('../../store/communication/restClient')

beforeEach(() => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)
})

test('check for elements', async () => {
  const {getByTestId} = render(
    <Providers>
      <AppLayout />
    </Providers>,
  )

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
  const {container, getByTestId} = render(
    <Providers>
      <AppLayout />
    </Providers>,
  )


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
  const {container, getByTestId} = render(
    <Providers>
      <AppLayout />
    </Providers>,
  )

  const userStore = new UserStore()
  await waitFor( () => {console.log(userStore.requestUser(email1.toString(), confirm1))
  })

  const loginButton = getByTestId(/login-button/)
  expect(loginButton).toBeInTheDocument()

})
