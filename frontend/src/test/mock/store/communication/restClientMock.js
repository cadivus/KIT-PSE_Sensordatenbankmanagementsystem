// eslint-disable-next-line @typescript-eslint/no-explicit-any
import getJsonMap from './mockData/backend/getJson'

export async function getJson(path: string): Promise<any> {
  const mockData = getJsonMap.get(path)
  return mockData
}
