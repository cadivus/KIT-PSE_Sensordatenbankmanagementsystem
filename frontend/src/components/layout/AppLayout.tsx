import React, {FC, useContext, useCallback} from 'react'
import {useHistory} from 'react-router-dom'
import {
  Button,
  IconButton,
  AppBar,
  Toolbar,
  Typography,
  FormControl,
  Select,
  Theme,
  MenuItem,
  InputLabel,
  createStyles,
} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'
import {AppContext, AppContextProvider} from '../../context'

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    footer: {
      marginTop: '35px',
    },
    formControl: {
      margin: theme.spacing(1),
      minWidth: 120,
    },
  }),
)

const LOCALES = {
  ENGLISH: 'en-us',
  GERMAN: 'de-de',
}

const AppLayout: FC = ({children}) => {
  const history = useHistory()
  const classes = useStyles()

  const { state, dispatch } = useContext(AppContext);

  const setLanguage = useCallback((locale) => {
    dispatch({
      type: 'setLocale',
      locale
    })
  }, []);

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
          <Button color="inherit" onClick={() => setLanguage(LOCALES.ENGLISH)}>
            <Typography variant="h6">
              English
            </Typography>
          </Button>

          <ul>
            <li><button disabled={state.locale === LOCALES.ENGLISH}
                        onClick={() => setLanguage(LOCALES.ENGLISH)}>English</button></li>
            <li><button disabled={state.locale === LOCALES.GERMAN}
                        onClick={() => setLanguage(LOCALES.GERMAN)}>German</button></li>
          </ul>
        </Toolbar>
      </AppBar>
      {children}
      <div className={classes.footer} />
    </>
  )
}

export default AppLayout
