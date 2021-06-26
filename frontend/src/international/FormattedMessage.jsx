import React from 'react'
import {FormattedMessage} from 'react-intl'

/**
 * This component is for loading messages from a language file depending on the language set.
 */
const FormattedString = props => <FormattedMessage {...props}>{s => s}</FormattedMessage>

export default FormattedString
