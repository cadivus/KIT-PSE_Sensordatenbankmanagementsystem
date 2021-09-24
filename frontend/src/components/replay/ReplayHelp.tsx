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
      <Button data-testid="help-button" variant="outlined" color="primary" onClick={handleClickOpen}>
        <FormattedMessage id="replaypage.help" />
      </Button>
      <Dialog
        data-testid="help-message"
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        maxWidth="lg"
      >
        <DialogTitle id="alert-dialog-title">
          <FormattedMessage id="replaypage.helpTextHedding" />
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            <FormattedMessage id="replaypage.helpText1" />
            <div />
            <FormattedMessage id="replaypage.helpText2" />
            <div />
            <FormattedMessage id="replaypage.helpText3" />
            <div />
            <FormattedMessage id="replaypage.helpText4" />
            <div />
            <FormattedMessage id="replaypage.helpText5" />
            <div />
            <FormattedMessage id="replaypage.helpText6" />
            <div />
            <FormattedMessage id="replaypage.helpText7" />
            <div />
            <FormattedMessage id="replaypage.helpText8" />
            <div />
            <FormattedMessage id="replaypage.helpText9" />
            <div />
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  )
}

export default ReplayHelp
