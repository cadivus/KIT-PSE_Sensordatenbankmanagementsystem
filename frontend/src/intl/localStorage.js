import debug from 'debug'

const log = debug('intl:localStorage')
const logError = log.extend('ERROR*', '::')

export const saveToStorage = (name, data) => {
  if (!window || !window.localStorage) {
    return
  }

  window.localStorage.setItem(name, JSON.stringify(data))
}

export const getFromStorage = name => {
  if (!window || !window.localStorage) {
    return null
  }

  try {
    return JSON.parse(window.localStorage.getItem(name))
  } catch (e) {
    logError(e)

    return null
  }
}
