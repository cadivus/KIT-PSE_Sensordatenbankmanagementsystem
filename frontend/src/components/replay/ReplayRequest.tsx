import React from 'react'
import {Button, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'

const useStyles = makeStyles({
  Margins: {
    marginTop: '3%',
  },
})

/**
 *  Displays the replay request.
 *  This class implements a React component.
 */
const ReplayRequest = () => {
  const classes = useStyles()

  return (
    <div>
      <Button variant="outlined" className={classes.Margins}>
        Play
      </Button>
      <Typography variant="h5" className={classes.Margins}>
        Request
      </Typography>
      <Typography variant="body1">xxxxxxxxxxxxxxxxxxx</Typography>
    </div>
  )
}

export default ReplayRequest
