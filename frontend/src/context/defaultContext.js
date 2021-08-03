const LOCALES = {
  ENGLISH: 'en-us',
  GERMAN: 'de-de',
}

const getFromStorage = name => {
  if (!window || !window.localStorage) {
    return null
  }

  try {
    return JSON.parse(window.localStorage.getItem(name))
  } catch (e) {
    console.error(e)

    return null
  }
}

export default {
  locale: getFromStorage('locale') || LOCALES.ENGLISH,
}
