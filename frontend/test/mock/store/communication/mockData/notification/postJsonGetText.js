import {email1} from './getText'
import {
  getPostSubscriptionPath,
  getSubscriptionsUrl,
  getUnsubscribePath,
} from '../../../../../../src/store/communication/notificationUrlCreator'
import EMail from '../../../../../../src/types/EMail'
import getJsonMap, {user1SubscriptionsFirst, user1SubscriptionsSecond} from './getJson'
import Id from '../../../../../../src/types/Id'
import {thing2Id} from '../backend/getJson'

export const getResponse = (path, input) => {
  if (path === getPostSubscriptionPath(new EMail(email1), new Id(thing2Id), 7, true)) {
    getJsonMap.set(getSubscriptionsUrl(new EMail(email1)), user1SubscriptionsSecond)
    return ''
  }

  if (path === getUnsubscribePath(new EMail(email1), new Id(thing2Id))) {
    getJsonMap.set(getSubscriptionsUrl(new EMail(email1)), user1SubscriptionsFirst)
    return ''
  }

  return undefined
}

export default getResponse
