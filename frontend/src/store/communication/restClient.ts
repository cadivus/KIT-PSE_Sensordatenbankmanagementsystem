import fetch from 'node-fetch'

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export async function getJson(path: string): Promise<any> {
  const response = await fetch(path)
  const data = await response.json()

  return data
}

export async function getText(path: string): Promise<string> {
  const response = await fetch(path)
  const data = await response.text()

  return data
}
