import type EMail from '../../types/EMail'
import Id from '../../types/Id'
import LoginCode from '../../types/LoginCode'

export const NOTIFICATION_PATH = `${window.location.protocol}//${window.location.host}/api/notification`

export const LOGIN_STATE_PATH = `${NOTIFICATION_PATH}/checkIfLoggedIn`

export const getConfirmCodeUrl = (email: EMail) => {
  return `${NOTIFICATION_PATH}/getConfirmCode/${email.toString()}`
}

export const getLoginUrl = (email: EMail, code: LoginCode) => {
  return `${NOTIFICATION_PATH}/login/${code.toString()}&${email.toString()}`
}

export const getSubscriptionsUrl = (email: EMail) => {
  return `${NOTIFICATION_PATH}/getSubscriptions/${email.toString()}`
}

export const getPostSubscriptionPath = (
  email: EMail,
  thingID: Id,
  notificationLevel: number,
  directNotification: boolean,
): string => {
  return `${NOTIFICATION_PATH}/postSubscription?mailAddress=${email.toString()}&sensorID=${thingID.toString()}&reportInterval=${notificationLevel}&toggleAlert=${directNotification}`
}

export const getUnsubscribePath = (email: EMail, thingID: Id): string => {
  return `${NOTIFICATION_PATH}/postUnsubscribe?mailAddress=${email.toString()}&sensorID=${thingID.toString()}`
}

export const LOGIN_SUCCESS_RESPONSE = 'Cookie created'
