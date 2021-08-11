import React from 'react'
import {Button, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'
import Replay from '../../material/Replay'

const useStyles = makeStyles({
  Margins: {
    marginTop: '3%',
  },
})

/**
 *  Displays the replay request.
 *  This class implements a React component.
 */
const ReplayRequest = ({replay}: {replay: Replay | null}) => {
  const classes = useStyles()

  if (replay) {
    return (
      <div>
        <Button variant="outlined" className={classes.Margins}>
          <FormattedMessage id="replaypage.play" />
        </Button>
        <Typography variant="h5" className={classes.Margins}>
          <FormattedMessage id="replaypage.request" />
        </Typography>
        <Typography variant="body1">xxxxxxxxxxxxxxxxxxx</Typography>
      </div>
    )
  }
  return <></>
}

export default ReplayRequest
