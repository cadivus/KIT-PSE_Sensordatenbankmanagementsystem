// eslint-disable-next-line @typescript-eslint/no-explicit-any
import getJsonMap from './mockData/backend/getJson'
import getTextMap from './mockData/backend/getText'

export async function getJson(path: string): Promise<any> {
  const mockData = getJsonMap.get(path)
  return mockData
}

export async function getText(path: string): Promise<string> {
  const mockData = getTextMap.get(path)
  return mockData
}
