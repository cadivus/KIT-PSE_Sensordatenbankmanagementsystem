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

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export async function postJsonGetJson(path: string, postJson: any): Promise<any> {
  const response = await fetch(path, {
    method: 'post',
    body: JSON.stringify(postJson),
    headers: {'Content-Type': 'application/json'},
  })
  const data = await response.json()

  return data
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export async function postJsonGetText(path: string, postJson: any): Promise<string> {
  const response = await fetch(path, {
    method: 'post',
    body: JSON.stringify(postJson),
    headers: {'Content-Type': 'application/json'},
  })
  const data = await response.text()

  return data
}
