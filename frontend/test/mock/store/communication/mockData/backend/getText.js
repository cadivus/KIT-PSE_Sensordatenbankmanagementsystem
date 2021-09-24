/* eslint-disable */
import {getExportDatastreamUrl} from '../../../../../../src/store/communication/backendUrlCreator'
import Id from '../../../../../../src/types/Id'
import {datastreamThing1Id, datastreamThing2Id, datastreamThing3Id} from './getJson'
import {thing1Datastream1Csv, thing2Datastream1Csv, thing3Datastream1Csv} from './files/csvFiles'
/* eslint-enable */

export const getTextMap = new Map()

getTextMap.set(getExportDatastreamUrl(new Id(datastreamThing1Id), 25), thing1Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamThing2Id), 25), thing2Datastream1Csv)
getTextMap.set(getExportDatastreamUrl(new Id(datastreamThing3Id), 25), thing3Datastream1Csv)

export default getTextMap
