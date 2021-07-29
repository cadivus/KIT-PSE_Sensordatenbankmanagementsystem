import React, {FC} from 'react'
import {useHistory} from 'react-router-dom'
import {Button, IconButton, AppBar, Toolbar, Typography} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from "react-intl";

const useStyles = makeStyles({
  footer: {
    marginTop: '35px',
  },
})

const AppLayout: FC = ({children}) => {
  const history = useHistory()
  const classes = useStyles()

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
        </Toolbar>
      </AppBar>
      {children}
      <div className={classes.footer} />
    </>
  )
}

export default AppLayout
