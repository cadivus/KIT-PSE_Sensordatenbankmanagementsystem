import type EMail from '../../material/EMail'

export const NOTIFICATION_PATH = `${window.location.protocol}//${window.location.host}/api/notification`

export const POST_SUBSCRIPTION_PATH = `${NOTIFICATION_PATH}/postSubscription`

export const POST_UBSUBSCRIBE_PATH = `${NOTIFICATION_PATH}/postUnsubscribe`

export const GET_SUBSCRIPTION_PATH = `${NOTIFICATION_PATH}/getSubscriptions`

export const getConfirmCodeUrl = (email: EMail) => {
  return `${NOTIFICATION_PATH}/getConfirmCode/${email.toString()}`
}
