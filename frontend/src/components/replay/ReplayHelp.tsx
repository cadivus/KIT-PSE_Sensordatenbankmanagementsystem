import React from 'react'
import {Button, Snackbar} from '@material-ui/core'

/**
 *  Displays the text of the helpbox for the replay.
 *  This class implements a React component.
 */
const ReplayHelp = () => {
  const [state, setState] = React.useState({
    open: false,
  })
  const {open} = state

  const handleClick = () => () => {
    setState({open: true})
  }

  const handleClose = () => {
    setState({...state, open: false})
  }

  return (
    <div>
      <Button variant="outlined" onClick={handleClick()}>
        Help
      </Button>
      <Snackbar
        anchorOrigin={{vertical: 'bottom', horizontal: 'right'}}
        open={open}
        onClose={handleClose}
        message="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore
        et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.
        Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet,
        consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.
        At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
      />
    </div>
  )
}

export default ReplayHelp
