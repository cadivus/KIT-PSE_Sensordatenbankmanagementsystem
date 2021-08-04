import React, {useContext} from 'react'
import {AppContext} from '../intl/components/Context'
import {I18nPropvider} from '../intl/components/i18n'

const ProviderWrapper = ({children}) => {
  const {state} = useContext(AppContext)

  return <I18nPropvider locale={state.locale}>{children}</I18nPropvider>
}

export default ProviderWrapper
