import ReplaySpeed from '../../material/ReplaySpeed'
import Thing from '../../material/Thing'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const dateFormat = require('dateformat')

const toBackendJsonDate = (date: Date) => dateFormat(date, 'dd/mm/yyyy HH:MM:ss')

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const createReplayRequestJson = (start: Date, end: Date, speed: ReplaySpeed, things: Set<Thing>): any => {
  const formattedStart = toBackendJsonDate(start)
  const formattedEnd = toBackendJsonDate(end)
  const speedNumber = speed.toNumber()
  const idList: Array<string> = Array.from(things).map(thing => thing.id.toString())

  const json = {
    start: formattedStart,
    end: formattedEnd,
    speed: speedNumber,
    sensors: idList,
  }

  return json
}
