/* eslint-disable */
import {
  ALL_THINGS,
  getActiveStateUrl,
  getAllThingDatastreamsUrl,
  getDatastreamUrl,
} from '../../../../../../store/communication/backendUrlCreator'
import Id from '../../../../../../types/Id'

export const sensor1Id = 'saqn:t:43ae704'
export const sensor2Id = 'saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001'
export const sensor3Id = 'saqn:t:4049564'

export const sensor1 = {"id":sensor1Id,"name":"Online Sensor Mock","description":"Low Cost Node Measuring Particulate Matter","properties":"{\"shortname\": \"crowdsensor\", \"hardware.id\": \"183100\", \"operator.domain\": \"teco.edu\"}","locations":[{"id":"geo:48.371540,10.898510,nan","name":"Oberländerstraße 73, 86161 Augsburg","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.89851,48.37154]}","geom":"0101000020E61000000742B28009CC2540F3936A9F8E2F4840","genFoiId":"ac1f4032-1c37-11ea-b2b8-f75e175e6959","properties":null}]}
export const sensor2 = {"id":sensor2Id,"name":"Offline Sensor Mock","description":"Mid Cost Device Measuring Particulate Matter","properties":"{\"shortname\": \"edm80neph\", \"hardware.id\": \"sn17001\", \"operator.domain\": \"grimm-aerosol.com\"}","locations":[{"id":"1ab23546-8555-11ea-a024-33cf9a467dab","name":"","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.896306,48.370278]}","geom":"0101000020E6100000CA6ABA9EE8CA2540D236FE44652F4840","genFoiId":null,"properties":null}]}
export const sensor3 = {"id":sensor3Id,"name":"Unknown State Sensor Mock","description":"A station measuring -Gaseous air pollutants- -Particulate air pollutants-","properties":"{\"dem_status\": \"M\", \"network_code\": \"DE007A\", \"operator_url\": \"lfu.bayern.de\", \"station_code\": \"DEBY110\", \"station_name\": \"Augsburg/Karlstraße\", \"type_of_station\": \"traffic\", \"station_altitude\": 485, \"station_end_date\": \"nan\", \"station_latitude_d\": 48.370237, \"station_local_code\": \"DEBY110\", \"station_start_date\": 20030801, \"station_longitude_d\": 10.896221, \"station_latitude_dms\": \"+048.22.12\", \"station_type_of_area\": \"urban\", \"station_longitude_dms\": \"+010.53.46\", \"station_ozone_classification\": \"urban\", \"station_subcat_rural_background\": \"unknown\"}","locations":[{"id":"geo:48.370237,10.896221,485.0","name":"Karlstraße Ecke Kesselmarkt, 86150 Augsburg","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.896221,48.370237,485.0]}","geom":"0101000020E610000012FA997ADDCA254074620FED632F4840","genFoiId":"f832296e-a3e5-11e9-b2c0-f3f0362af6f7","properties":null}]}

export const allThings = [sensor1, sensor2, sensor3]

export const datastreamSensor1Id = 'saqn:ds:d98d0a2'
export const datastreamSensor2Id = 'saqn:ds:ffc6f61'
export const datastreamSensor3Id = 'saqn:ds:b88cfcb'

export const sensor1Datastream1Start = '22/11/2019@07:17:46'
export const sensor1Datastream1End = '27/01/2020@07:51:30'
export const sensor2Datastream1Start = '01/11/2018@01:10:08'
export const sensor2Datastream1End = '04/02/2020@11:04:55'
export const sensor3Datastream1Start = '31/12/2016@12:00:00'
export const sensor3Datastream1End = '11/07/2019@02:00:00'

export const sensor1Datastream1 = {"id":datastreamSensor1Id,"name":"PM2.5 Datastream of Crowdsensing Node (SDS011, 183100)","description":"Datastream for recording Particulate Matter","sensorId":"saqn:s:b3dd2c9","phenomenonStart":sensor1Datastream1Start,"phenomenonEnd":sensor1Datastream1End,"resultTimeStart":sensor1Datastream1Start,"resultTimeEnd":sensor1Datastream1End,"obsId":"saqn:op:mcpm2p5","unit":"microgram per cubic meter"}
export const sensor2Datastream1 = {"id":datastreamSensor2Id,"name":"Offset-Bestimmung (Auto-Zero)","description":"Das Gerät führt einen Auto-Zero durch. Das bedeutet der E-Filter ist eingeschaltet und filtert alle Partikel aus der Luft (Null-Luft).","sensorId":"saqn:s:1e51f2e","phenomenonStart":sensor2Datastream1Start,"phenomenonEnd":sensor2Datastream1End,"resultTimeStart":sensor2Datastream1Start,"resultTimeEnd":sensor2Datastream1End,"obsId":"saqn:op:cal_edm80neph_zero_det","unit":"Array of calibration data"}
export const sensor3Datastream1 = {"id":datastreamSensor3Id,"name":"Particulate matter - PM10, first measurement Datastream of station DEBY110","description":"A Datastream measuring Particulate matter - PM10, first measurement using nephelometry and beta attenuation","sensorId":"saqn:s:fa2dbc8","phenomenonStart":sensor3Datastream1Start,"phenomenonEnd":sensor3Datastream1End,"resultTimeStart":null,"resultTimeEnd":null,"obsId":"saqn:op:mcpm10","unit":"microgram per cubic meter"}
export const allDatastreams = [sensor1Datastream1, sensor2Datastream1, sensor3Datastream1]

export const sensor1Datastreams = [sensor1Datastream1]
export const sensor2Datastreams = [sensor2Datastream1]
export const sensor3Datastreams = [sensor3Datastream1]

const activeStateSensor1 = [true]
const activeStateSensor2 = [false]
const activeStateSensor3 = [0]
/* eslint-enable */

export const getJsonMap = new Map()
getJsonMap.set(ALL_THINGS, allThings)

getJsonMap.set(getActiveStateUrl(new Id(sensor1Id)), activeStateSensor1)
getJsonMap.set(getActiveStateUrl(new Id(sensor2Id)), activeStateSensor2)
getJsonMap.set(getActiveStateUrl(new Id(sensor3Id)), activeStateSensor3)

getJsonMap.set(getAllThingDatastreamsUrl(new Id(sensor1Id)), sensor1Datastreams)
getJsonMap.set(getDatastreamUrl(new Id(datastreamSensor1Id)), sensor1Datastream1)

getJsonMap.set(getAllThingDatastreamsUrl(new Id(sensor2Id)), sensor2Datastreams)
getJsonMap.set(getDatastreamUrl(new Id(datastreamSensor2Id)), sensor2Datastream1)

getJsonMap.set(getAllThingDatastreamsUrl(new Id(sensor3Id)), sensor3Datastreams)
getJsonMap.set(getDatastreamUrl(new Id(datastreamSensor3Id)), sensor3Datastream1)

export default getJsonMap
