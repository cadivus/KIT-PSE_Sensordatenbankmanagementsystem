import fetch from 'node-fetch'

export async function getJson(path): any {
  const pathh=`${window.location.protocol}//${window.location.host}/api/backend`

  const response = await fetch(pathh)
  const data = await response.json()

  return data
}

export async function getText(path): any {
  const pathh=`${window.location.protocol}//${window.location.host}/api/backend`

  const response = await fetch(pathh)
  const data = await response.text()

  return data
}
