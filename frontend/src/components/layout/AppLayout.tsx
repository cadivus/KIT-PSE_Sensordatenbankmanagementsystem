import React, {FC, useCallback, useContext} from 'react'
import {useHistory} from 'react-router-dom'
import {Button, IconButton, AppBar, Toolbar, Typography} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'
import {AppContext} from '../../intl/components/Context'
import {LOCALES} from '../../intl/components/i18n'

const useStyles = makeStyles({
  footer: {
    marginTop: '35px',
  },
})

const AppLayout: FC = ({children}) => {
  const history = useHistory()
  const classes = useStyles()
  const {state, dispatch} = useContext(AppContext)

  const setLanguage = useCallback(locale => {
    dispatch({
      type: 'setLocale',
      locale,
    })
  }, [])

  const language = state.locale === LOCALES.ENGLISH ? 'Deutsch' : 'English'

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="menu" onClick={() => history.push('/')}>
            <HomeIcon />
          </IconButton>
          <Button color="inherit" onClick={() => history.push('/subscriptions')}>
            <Typography variant="h6">
              <FormattedMessage id="appbar.subscription" />
            </Typography>
          </Button>
          <Button color="inherit" onClick={() => history.push('/login')}>
            <Typography variant="h6">
              <FormattedMessage id="appbar.login" />
            </Typography>
          </Button>

          <Button
            color="inherit"
            onClick={() => setLanguage(state.locale === LOCALES.ENGLISH ? LOCALES.GERMAN : LOCALES.ENGLISH)}
          >
            <Typography variant="h6">{language}</Typography>
          </Button>
        </Toolbar>
      </AppBar>
      {children}
      <div className={classes.footer} />
    </>
  )
}

export default AppLayout
