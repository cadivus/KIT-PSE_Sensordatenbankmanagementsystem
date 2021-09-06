import {email1} from './getText'
import {sensor1Id, sensor2Id} from '../backend/getJson'
import {getSubscriptionsUrl} from '../../../../../../store/communication/notificationUrlCreator'

export const user1SubscriptionsFirst = [
  {
    id: 19,
    subscriberAddress: email1.toString(),
    sensorId: sensor1Id,
    subTime: '2021-08-31',
    reportInterval: 6,
    toggleAlert: true,
  },
]
export const user1SubscriptionsSecond = [
  {
    id: 19,
    subscriberAddress: email1.toString(),
    sensorId: sensor1Id,
    subTime: '2021-08-31',
    reportInterval: 6,
    toggleAlert: true,
  },
  {
    id: 20,
    subscriberAddress: email1.toString(),
    sensorId: sensor2Id,
    subTime: '2021-08-31',
    reportInterval: 7,
    toggleAlert: false,
  },
]

export const getJsonMap = new Map()

getJsonMap.set(getSubscriptionsUrl(email1), user1SubscriptionsFirst)

export default getJsonMap
