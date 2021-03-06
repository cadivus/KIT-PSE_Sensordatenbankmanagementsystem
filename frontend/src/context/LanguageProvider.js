import React, {Fragment} from 'react'
import PropTypes from 'prop-types'
import {IntlProvider} from 'react-intl'
import flatten from 'flat'

import {LOCALES} from '../intl/constants'
import messages from '../intl/messages/messages'

const LanguageProvider = ({children, locale = LOCALES.ENGLISH}) => (
  <IntlProvider textComponent={Fragment} locale={locale} messages={flatten(messages[locale])}>
    {children}
  </IntlProvider>
)

LanguageProvider.displayName = 'I18nProvider'

LanguageProvider.propTypes = {
  children: PropTypes.oneOfType([PropTypes.arrayOf(PropTypes.node), PropTypes.node]).isRequired,
  locale: PropTypes.oneOf(Object.values(LOCALES)),
}

LanguageProvider.defaultProps = {
  locale: LOCALES.GERMAN,
}

export default LanguageProvider
