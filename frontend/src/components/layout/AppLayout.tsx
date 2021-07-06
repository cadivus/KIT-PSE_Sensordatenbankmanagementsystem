import React, {FC} from 'react'
import {useHistory} from 'react-router-dom'
import {Button, IconButton, AppBar, Toolbar, Typography} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home'

const AppLayout: FC = ({children}) => {
  const history = useHistory()

  return (
    <>
      <AppBar position='static'>
        <Toolbar>
          <IconButton edge='start' color='inherit' aria-label='menu' onClick={() => history.push('/')}>
            <HomeIcon/>
          </IconButton>
          <Button color='inherit' onClick={() => history.push("/subscriptions")}>
            <Typography variant='h6'>
              Subscriptions
            </Typography>
          </Button>
          <Button color='inherit' onClick={() => history.push("/login")}>
            <Typography variant='h6'>
              Login
            </Typography>
          </Button>
        </Toolbar>
      </AppBar>
      {children}
    </>
  )
}

export default AppLayout
