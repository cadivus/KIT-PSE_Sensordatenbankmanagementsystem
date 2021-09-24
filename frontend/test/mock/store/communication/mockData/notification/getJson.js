import {email1} from './getText'
import {thing1Id, thing2Id} from '../backend/getJson'
import {getSubscriptionsUrl} from '../../../../../../src/store/communication/notificationUrlCreator'

export const user1SubscriptionsFirst = [
  {
    id: 19,
    subscriberAddress: email1.toString(),
    sensorId: thing1Id,
    subTime: '2021-08-31',
    reportInterval: 6,
    toggleAlert: true,
  },
]
export const user1SubscriptionsSecond = [
  {
    id: 19,
    subscriberAddress: email1.toString(),
    sensorId: thing1Id,
    subTime: '2021-08-31',
    reportInterval: 6,
    toggleAlert: true,
  },
  {
    id: 20,
    subscriberAddress: email1.toString(),
    sensorId: thing2Id,
    subTime: '2021-08-31',
    reportInterval: 7,
    toggleAlert: false,
  },
]

export const getJsonMap = new Map()

getJsonMap.set(getSubscriptionsUrl(email1), user1SubscriptionsFirst)

export default getJsonMap
