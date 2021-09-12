import {CREATE_REPLAY} from '../../../../../../store/communication/backendUrlCreator'
import {thing1Id, thing2Id, thing3Id} from './getJson'

const dateRegex = new RegExp('^\\d\\d\\/\\d\\d\\/\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d$', 'i')
const numberRegex = new RegExp('^\\d*$|^\\d+\\.\\d+$', 'i')

export const thing1Replay = '7447dcb5-68ba-4e73-97f4-e3488aec045a'
export const thing2Replay = '1653c237-7ff9-49b3-a615-e9661044141d'
export const thing3Replay = '0220400b-9ce2-4e94-9dc9-f6f43ceb4d7b'
export const elseReplay = 'd79ffbe4-15f5-4c5a-8ccc-256f9db9c845'

const getNewSseResponse = input => {
  const {start, end, speed, sensors} = input
  if (!dateRegex.test(start)) return undefined
  if (!dateRegex.test(end)) return undefined
  if (!numberRegex.test(speed)) return undefined
  if (sensors.length === 0) return undefined

  if (sensors.length === 1) {
    const onlySensor = sensors[0]
    if (onlySensor === thing1Id) return thing1Replay
    if (onlySensor === thing2Id) return thing2Replay
    if (onlySensor === thing3Id) return thing3Replay
  }

  return elseReplay
}

export const getResponse = (path, input) => {
  if (path === CREATE_REPLAY) return getNewSseResponse(input)

  return undefined
}

export default getResponse
