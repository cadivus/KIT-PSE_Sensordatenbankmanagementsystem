import UserStore from './UserStore'
import {getJson, getText, postJsonGetText} from './communication/restClient'
import {
  getJson as getJsonMock,
  getText as getTextMock,
  postJsonGetText as postJsonGetTextMock,
} from '../test/mock/store/communication/restClientMock'
import {confirm1, confirm2, email1} from '../test/mock/store/communication/mockData/notification/getText'
import LoginCode from '../types/LoginCode'
import SubscriptionStore from './SubscriptionStore'
import DatastreamStore from './DatastreamStore'
import ThingStore from './ThingStore'
import {
  user1SubscriptionsFirst,
  user1SubscriptionsSecond,
} from '../test/mock/store/communication/mockData/notification/getJson'
import subscriptionCollectionMatches from '../test/matchTest/material/SubscriptionMatch'
import {thing2Id} from '../test/mock/store/communication/mockData/backend/getJson'
import NotificationLevel from '../types/NotificationLevel'

jest.mock('./communication/restClient')

const initLoggedInUser = async () => {
  getJson.mockImplementation(getJsonMock)
  getText.mockImplementation(getTextMock)
  postJsonGetText.mockImplementation(postJsonGetTextMock)

  const userStore = new UserStore()
  await userStore.requestStep1(email1)
  const user = await userStore.requestUser(email1, new LoginCode(confirm1))

  const thingStore = new ThingStore(new DatastreamStore())
  const subscriptionStore = new SubscriptionStore(thingStore)
  subscriptionStore.user = user

  return {subscriptionStore, userStore, thingStore}
}

describe('logged in user', () => {
  it('get subscriptions', async () => {
    const {subscriptionStore} = await initLoggedInUser()
    const subscriptions = await subscriptionStore.getSubscriptions()
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsFirst)
  })

  it('get subscriptions', async () => {
    const {subscriptionStore, thingStore} = await initLoggedInUser()
    const subscriptions = await subscriptionStore.getSubscriptions()
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsFirst)

    const newThing = await thingStore.getThing(thing2Id)
    const directNotification = false
    const notificationLevel = new NotificationLevel(7, true)
    subscriptionStore.createSubscription(newThing, directNotification, notificationLevel)
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsSecond)
  })

  it('unsubscribe', async () => {
    const {subscriptionStore, thingStore} = await initLoggedInUser()
    const subscriptions = await subscriptionStore.getSubscriptions()
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsFirst)

    const newThing = await thingStore.getThing(thing2Id)
    const directNotification = false
    const notificationLevel = new NotificationLevel(7, true)
    const newSubscription = subscriptionStore.createSubscription(newThing, directNotification, notificationLevel)
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsSecond)

    await newSubscription.unsubscribe()
    subscriptionCollectionMatches(subscriptions, user1SubscriptionsFirst)
  })
})
