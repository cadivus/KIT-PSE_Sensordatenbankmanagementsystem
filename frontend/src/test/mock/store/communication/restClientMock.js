// eslint-disable-next-line @typescript-eslint/no-explicit-any
import * as getJsonBackend from './mockData/backend/getJson'
import * as getTextBackend from './mockData/backend/getText'
import * as postJsonGetTextBackend from './mockData/backend/postJsonGetText'

import * as getTextNotification from './mockData/notification/getText'
import * as postJsonGetNotification from './mockData/notification/postJsonGetText'

export async function getJson(path: string): Promise<any> {
  const mockData = getJsonBackend.getJsonMap.get(path)
  return mockData
}

export async function getText(path: string): Promise<string> {
  let mockData = getTextBackend.getTextMap.get(path)
  if (!mockData) mockData = getTextNotification.getTextMap.get(path)
  return mockData
}

export async function postJsonGetText(path: string, postJson: any): Promise<string> {
  let mockData = postJsonGetTextBackend.getResponse(path, postJson)
  if (!mockData) mockData = postJsonGetNotification.getResponse(path, postJson)
  return mockData
}
