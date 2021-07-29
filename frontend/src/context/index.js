import React, {useReducer, createContext} from 'react'
import defaultContext from './defaultContext'

const AppContext = createContext()

const saveToStorage = (name, data) => {
  if (!window || !window.localStorage) {
    return
  }

  window.localStorage.setItem(name, JSON.stringify(data))
}

const reducer = (state, action) => {
  switch (action.type) {
    case 'reset':
      return defaultContext
    case 'setLocale':
      saveToStorage('locale', action.locale)
      return {...state, locale: action.locale}
  }
}

const AppContextProvider = ({children}) => {
  const [state, dispatch] = useReducer(reducer, defaultContext)
  const value = {state, dispatch}

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>
}

export {AppContext, AppContextProvider}
