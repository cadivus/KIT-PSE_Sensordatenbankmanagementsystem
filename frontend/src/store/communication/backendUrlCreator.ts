import Id from '../../material/Id'

export const BACKEND_PATH = `${window.location.protocol}//${window.location.host}/api/backend`

export const ALL_THINGS = `${BACKEND_PATH}/sensor/allThings`

export const getActiveStateUrl = (thingId: Id): string => `${BACKEND_PATH}/sensor/active?ids=${thingId.toString()}`

export const getAllThingDatastreamsUrl = (thingId: Id): string =>
  `${BACKEND_PATH}/datastream/listDatastreams?id=${thingId.toString()}`

export const getExportDatastreamUrl = (datastreamId: Id, limit: number): string =>
  `${BACKEND_PATH}/datastream/export?id=${datastreamId.toString()}${limit < 1 ? '' : `&limit=${limit}`}`

export const CREATE_REPLAY = `${BACKEND_PATH}/createReplay`
