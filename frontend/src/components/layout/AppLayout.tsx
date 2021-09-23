import React, {FC, useCallback, useContext, useEffect, useState} from 'react'
import {useHistory} from 'react-router-dom'
import {Button, IconButton, AppBar, Toolbar, Typography} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'
import {AppContext} from '../../intl/AppContextProvider'
import {LOCALES} from '../../intl/constants'
import useUserStore from '../../hooks/UseUserStore'

const useStyles = makeStyles({
  footer: {
    marginTop: '35px',
  },
  loginButton: {
    position: 'absolute',
    right: '20px',
  },
})

const AppLayout: FC = ({children}) => {
  const history = useHistory()
  const classes = useStyles()
  const {state, dispatch} = useContext(AppContext)
  const userStore = useUserStore()
  const [loggedIn, setLoggedIn] = useState(false)

  useEffect(() => {
    const updateLoggedIn = () => {
      setLoggedIn(!!userStore?.user)
    }
    userStore?.on('login-change', updateLoggedIn)

    return () => {
      userStore?.off('login-change', updateLoggedIn)
    }
  }, [userStore, setLoggedIn])

  const setLanguage = useCallback(
    locale => {
      dispatch({
        type: 'setLocale',
        locale,
      })
    },
    [dispatch],
  )

  const language = state.locale === LOCALES.ENGLISH ? 'Deutsch' : 'English'
  const logout = () => {
    userStore?.user?.logout()
    history.push('/')
  }

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            data-testid="home-button"
            edge="start"
            color="inherit"
            aria-label="menu"
            onClick={() => history.push('/')}
          >
            <HomeIcon />
          </IconButton>
          <Button data-testid="subscription-button" color="inherit" onClick={() => history.push('/subscriptions')}>
            <Typography variant="h6">
              <FormattedMessage id="appbar.subscription" />
            </Typography>
          </Button>
          <Button
            data-testid="language-button"
            color="inherit"
            onClick={() => setLanguage(state.locale === LOCALES.ENGLISH ? LOCALES.GERMAN : LOCALES.ENGLISH)}
          >
            <Typography variant="h6">{language}</Typography>
          </Button>
          {!loggedIn && (
            <Button
              data-testid="login-button"
              className={classes.loginButton}
              color="inherit"
              onClick={() => history.push('/login')}
            >
              <Typography variant="h6">
                <FormattedMessage id="appbar.login" />
              </Typography>
            </Button>
          )}
          {userStore?.user && (
            <Button data-testid="logout-button" className={classes.loginButton} color="inherit" onClick={logout}>
              <Typography variant="h6">
                <FormattedMessage id="appbar.logout" />
              </Typography>
            </Button>
          )}
        </Toolbar>
      </AppBar>
      {children}
      <div className={classes.footer} />
    </>
  )
}

export default AppLayout
