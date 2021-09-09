import EMail from '../../../../../../types/EMail'
import {getConfirmCodeUrl} from '../../../../../../store/communication/notificationUrlCreator'

export const email1 = new EMail('test1@test.tld')
export const email2 = new EMail('test2@test2.tttld')

export const confirm1 = 'TbzVFyap'
export const confirm2 = 'XcQRZTqP'

export const getTextMap = new Map()

getTextMap.set(getConfirmCodeUrl(email1), confirm1)
getTextMap.set(getConfirmCodeUrl(email2), confirm2)

export default getTextMap
