import React, {FC} from 'react'
import {Button, IconButton, AppBar, Toolbar} from '@material-ui/core'
import MenuIcon from '@material-ui/icons/Menu'

const AppLayout: FC = ({children}) => {
  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <IconButton edge="start" color="inherit" aria-label="menu">
            <MenuIcon />
          </IconButton>
          <Button color="inherit">Login</Button>
        </Toolbar>
      </AppBar>
      {children}
    </>
  )
}

export default AppLayout
