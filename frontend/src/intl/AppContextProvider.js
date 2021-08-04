import React, {useReducer, createContext} from 'react'
import defaultContext from './defaultContext'
import {saveToStorage} from './localStorage'

const STORAGE_KEY = 'locale'
const AppContext = createContext()

const reducer = (state, action) => {
  switch (action.type) {
    case 'reset':
      return defaultContext
    case 'setLocale':
      saveToStorage(STORAGE_KEY, action.locale)
      return {...state, locale: action.locale}
  }
}

const AppContextProvider = ({children}) => {
  const [state, dispatch] = useReducer(reducer, defaultContext)
  const value = {state, dispatch}

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>
}

export {AppContext, AppContextProvider}
