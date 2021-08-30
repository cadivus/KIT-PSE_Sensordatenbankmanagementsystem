// eslint-disable-next-line @typescript-eslint/no-explicit-any
import getJsonMap from './mockData/backend/getJson'
import getTextMap from './mockData/backend/getText'
import getResponse from './mockData/backend/postJsonGetText'

export async function getJson(path: string): Promise<any> {
  const mockData = getJsonMap.get(path)
  return mockData
}

export async function getText(path: string): Promise<string> {
  const mockData = getTextMap.get(path)
  return mockData
}

export async function postJsonGetText(path: string, postJson: any): Promise<string> {
  const mockData = getResponse(path, postJson)
  return mockData
}
