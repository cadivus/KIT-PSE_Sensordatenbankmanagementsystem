/* eslint-disable */
import {getExportDatastreamUrl} from '../../../../../../store/communication/backendUrlCreator'
import Id from '../../../../../../material/Id'
import {datastreamSensor1Id, datastreamSensor2Id, datastreamSensor3Id} from './getJson'
import {sensor1Datastream1Csv, sensor2Datastream1Csv, sensor3Datastream1Csv} from './files/csvFiles'
/* eslint-enable */

export const getTextMap = new Map()

getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor1Id), 25), sensor1Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor2Id), 25), sensor2Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor3Id), 25), sensor3Datastream1Csv)

export default getTextMap
