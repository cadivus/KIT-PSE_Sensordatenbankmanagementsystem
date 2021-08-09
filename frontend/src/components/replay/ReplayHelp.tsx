import React from 'react'
import {Button, Dialog, DialogContent, DialogContentText, DialogTitle} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'

const useStyles = makeStyles({
  Margins: {
    marginTop: '5%',
    marginLeft: '87%',
  },
})

/**
 *  Displays the text of the helpbox for the replay.
 *  This class implements a React component.
 */
const ReplayHelp = () => {
  const [open, setOpen] = React.useState(false)
  const classes = useStyles()

  const handleClickOpen = () => {
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }

  return (
    <div className={classes.Margins}>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        <FormattedMessage id="replaypage.help" />
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          <FormattedMessage id="replaypage.helpTextHedding" />
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            <FormattedMessage id="replaypage.helpText" />
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default ReplayHelp
