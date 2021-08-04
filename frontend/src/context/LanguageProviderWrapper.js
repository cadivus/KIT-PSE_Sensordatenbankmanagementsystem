import React, {useContext} from 'react'

import {AppContext} from '.'
import {I18nPropvider} from '../intl/const'

const ProviderWrapper = ({children}) => {
  const {state} = useContext(AppContext)

  return <I18nPropvider locale={state.locale}>{children}</I18nPropvider>
}

export default ProviderWrapper
