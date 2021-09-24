import UserStore from './UserStore'
import {getJson, getText, postJsonGetText} from './communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../test/mock/store/communication/restClientMock'
import {confirm1, confirm2, email1} from '../test/mock/store/communication/mockData/notification/getText'
import LoginCode from '../types/LoginCode'

jest.mock('./communication/restClient')

const initValue = () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const userStore = new UserStore()

  return userStore
}

test('test login 1', async () => {
  const store = initValue()

  await store.requestStep1(email1)
  const user = await store.requestUser(email1, new LoginCode(confirm1))
  expect(user.email.toString()).toBe(email1.toString())
})

test('test invalid login 1', async () => {
  const store = initValue()

  await store.requestStep1(email1)
  const user = await store.requestUser(email1, new LoginCode(confirm2))
  expect(user).toBe(null)
})

test('test logout user 1', async () => {
  const store = initValue()

  await store.requestStep1(email1)
  const user = await store.requestUser(email1, new LoginCode(confirm1))
  expect(user.email.toString()).toBe(email1.toString())
  user.logout()

  expect(store.user).toBe(null)
})
