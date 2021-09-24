import EMail from '../../../../../../src/types/EMail'
import {
  getConfirmCodeUrl,
  getLoginUrl,
  LOGIN_SUCCESS_RESPONSE,
} from '../../../../../../src/store/communication/notificationUrlCreator'

export const email1 = new EMail('test1@test.tld')
export const email2 = new EMail('test2@test2.tttld')

export const confirm1 = 'TbzVFyap'
export const confirm2 = 'XcQRZTqP'

export const getTextMap = new Map()

getTextMap.set(getConfirmCodeUrl(email1), confirm1)
getTextMap.set(getConfirmCodeUrl(email2), confirm2)

getTextMap.set(getLoginUrl(email1, confirm1), LOGIN_SUCCESS_RESPONSE)
getTextMap.set(getLoginUrl(email2, confirm2), LOGIN_SUCCESS_RESPONSE)

export default getTextMap
