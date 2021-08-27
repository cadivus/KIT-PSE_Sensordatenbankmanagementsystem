/* eslint-disable */

import {
  ALL_THINGS,
  getActiveStateUrl,
  getAllThingDatastreamsUrl
} from '../../../../../../store/communication/backendUrlCreator'
import Id from '../../../../../../material/Id'

const allThings = [{"id":"saqn:t:43ae704","name":"Online Sensor Mock","description":"Low Cost Node Measuring Particulate Matter","properties":"{\"shortname\": \"crowdsensor\", \"hardware.id\": \"183100\", \"operator.domain\": \"teco.edu\"}","locations":[{"id":"geo:48.371540,10.898510,nan","name":"Oberländerstraße 73, 86161 Augsburg","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.89851,48.37154]}","geom":"0101000020E61000000742B28009CC2540F3936A9F8E2F4840","genFoiId":"ac1f4032-1c37-11ea-b2b8-f75e175e6959","properties":null}]},
  {"id":"saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001","name":"Offline Sensor Mock","description":"Mid Cost Device Measuring Particulate Matter","properties":"{\"shortname\": \"edm80neph\", \"hardware.id\": \"sn17001\", \"operator.domain\": \"grimm-aerosol.com\"}","locations":[{"id":"1ab23546-8555-11ea-a024-33cf9a467dab","name":"","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.896306,48.370278]}","geom":"0101000020E6100000CA6ABA9EE8CA2540D236FE44652F4840","genFoiId":null,"properties":null}]},
  {"id":"saqn:t:4049564","name":"Unknown State Sensor Mock","description":"A station measuring -Gaseous air pollutants- -Particulate air pollutants-","properties":"{\"dem_status\": \"M\", \"network_code\": \"DE007A\", \"operator_url\": \"lfu.bayern.de\", \"station_code\": \"DEBY110\", \"station_name\": \"Augsburg/Karlstraße\", \"type_of_station\": \"traffic\", \"station_altitude\": 485, \"station_end_date\": \"nan\", \"station_latitude_d\": 48.370237, \"station_local_code\": \"DEBY110\", \"station_start_date\": 20030801, \"station_longitude_d\": 10.896221, \"station_latitude_dms\": \"+048.22.12\", \"station_type_of_area\": \"urban\", \"station_longitude_dms\": \"+010.53.46\", \"station_ozone_classification\": \"urban\", \"station_subcat_rural_background\": \"unknown\"}","locations":[{"id":"geo:48.370237,10.896221,485.0","name":"Karlstraße Ecke Kesselmarkt, 86150 Augsburg","description":"","encodingType":"application/vnd.geo+json","location":"{\"type\":\"Point\",\"coordinates\":[10.896221,48.370237,485.0]}","geom":"0101000020E610000012FA997ADDCA254074620FED632F4840","genFoiId":"f832296e-a3e5-11e9-b2c0-f3f0362af6f7","properties":null}]}]

const datastreamSensor1 = {"id":"saqn:ds:d98d0a2","name":"PM2.5 Datastream of Crowdsensing Node (SDS011, 183100)","description":"Datastream for recording Particulate Matter","sensorId":"saqn:s:b3dd2c9","phenomenonStart":"22/11/2019@07:17:46","phenomenonEnd":"27/01/2020@07:51:30","resultTimeStart":"22/11/2019@07:17:46","resultTimeEnd":"27/01/2020@07:51:30","obsId":"saqn:op:mcpm2p5","unit":"microgram per cubic meter"}

export const getJsonMap = new Map()
getJsonMap.set(ALL_THINGS, allThings)

getJsonMap.set(getActiveStateUrl(new Id('saqn:t:43ae704')), [0])
getJsonMap.set(getActiveStateUrl(new Id('saqn:t:grimm-aerosol.com:EDM80NEPH:SN17001')), [0])
getJsonMap.set(getActiveStateUrl(new Id('saqn:t:4049564')), [0])

getJsonMap.set(getAllThingDatastreamsUrl(new Id('saqn:t:43ae704')), {datastreamSensor1})

export default getJsonMap
