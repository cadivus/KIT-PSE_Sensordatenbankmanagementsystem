import React, {useContext} from 'react'
import {AppContext} from '../intl/AppContextProvider'
import LanguageProvider from './LanguageProvider'

const ProviderWrapper = ({children}) => {
  const {state} = useContext(AppContext)

  return <LanguageProvider locale={state.locale}>{children}</LanguageProvider>
}

export default ProviderWrapper
