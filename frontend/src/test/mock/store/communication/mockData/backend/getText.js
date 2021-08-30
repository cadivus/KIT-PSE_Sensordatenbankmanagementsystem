/* eslint-disable */
import {getExportDatastreamUrl} from '../../../../../../store/communication/backendUrlCreator'
import Id from '../../../../../../material/Id'
import {datastreamSensor1Id, datastreamSensor2Id, datastreamSensor3Id} from './getJson'
import {sensor1Datastream1Csv, sensor2Datastream1Csv, sensor3Datastream1Csv} from './files/csvFiles'
import EMail from '../../../../../../material/EMail'
import {getConfirmCodeUrl} from '../../../../../../store/communication/notificationUrlCreator'
/* eslint-enable */

export const email1 = new EMail('test1@test.tld')
export const email2 = new EMail('test2@test2.tttld')

export const confirm1 = 'TbzVFyap'
export const confirm2 = 'XcQRZTqP'

export const getTextMap = new Map()

getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor1Id), 25), sensor1Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor2Id), 25), sensor2Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor3Id), 25), sensor3Datastream1Csv)

getTextMap.set(getConfirmCodeUrl(email1), confirm1)
getTextMap.set(getConfirmCodeUrl(email2), confirm2)

export default getTextMap
