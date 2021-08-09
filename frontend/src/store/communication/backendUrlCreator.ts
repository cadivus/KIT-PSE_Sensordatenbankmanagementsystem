import Id from '../../material/Id'

export const BACKEND_PATH = `${window.location.protocol}//${window.location.host}/api/backend`

export const ALL_THINGS = `${BACKEND_PATH}/sensor/allThings`

export const getActiveStateUrl = (sensorId: Id): string => `${BACKEND_PATH}/sensor/active?ids=${sensorId.toString()}`

export const getAllSensorDatastreamsUrl = (sensorId: Id): string =>
  `${BACKEND_PATH}/datastream/listDatastreams?id=${sensorId.toString()}`

export const getExportDatastreamUrl = (datastreamId: Id): string =>
  `${BACKEND_PATH}/datastream/export?id=${datastreamId.toString()}`
