import {getFromStorage} from './localStorage'
import {LOCALES} from './constants'

const STORAGE_KEY = 'locale'

export default {
  locale: getFromStorage(STORAGE_KEY) || LOCALES.ENGLISH,
}
