/* eslint-disable */
import {getExportDatastreamUrl} from '../../../../../../store/communication/backendUrlCreator'
import Id from '../../../../../../material/Id'
import {datastreamSensor1Id, datastreamSensor2Id, datastreamSensor3Id} from './getJson'
import {datastream1csv, datastream2csv, datastream3csv} from './files/csvFiles'
/* eslint-enable */

export const getTextMap = new Map()

getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor1Id), 25), datastream1csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor2Id), 25), datastream2csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamSensor3Id), 25), datastream3csv)

export default getTextMap
