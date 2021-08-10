import React, {FC} from 'react'
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Theme,
  Typography,
  withStyles,
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import {useHistory} from 'react-router-dom'
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import Checkbox from '@material-ui/core/Checkbox'
import {FormattedMessage} from 'react-intl'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import Subscription from '../../material/Subscription'

const StyledTableCell = withStyles((theme: Theme) =>
  createStyles({
    head: {
      backgroundColor: theme.palette.common.white,
      color: theme.palette.common.black,
      fontSize: 17,
    },
    body: {
      fontSize: 14,
    },
  }),
)(TableCell)

const StyledTableRow = withStyles((theme: Theme) =>
  createStyles({
    root: {
      backgroundColor: theme.palette.common.white,
    },
  }),
)(TableRow)

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      marginTop: '2%',
    },
    root: {
      '&$disabled': {
        color: theme.palette.primary.main,
      },
    },
    unsubscribe: {
      marginLeft: '89.5%',
      marginTop: '15px',
    },
  }),
)

/**
 *  Displays a list of subscriptions.
 *  This class implements a React component.
 */
const SubscriptionList: FC = () => {
  const classes = useStyles()
  const history = useHistory()
  const subscriptionStore = useSubscriptionStore()

  const [open, setOpen] = React.useState(false)
  const [clickedSubscription, setClickedSubscription] = React.useState<Subscription | null>(null)

  const handleClickOpen = (subscription: Subscription) => {
    setClickedSubscription(subscription)
    setOpen(true)
  }

  const handleClose = () => {
    setOpen(false)
  }

  const handleDelete = () => {
    if (clickedSubscription) clickedSubscription.unsubscribe()
    setClickedSubscription(null)
    setOpen(false)
  }

  return (
    <>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <StyledTableCell>
                <Typography variant="h5">
                  <ArrowDropDownIcon />
                  <FormattedMessage id="subscriptionpage.thing" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="h5">
                  <ArrowDropDownIcon />
                  <FormattedMessage id="subscriptionpage.logLevel" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="h5">
                  <ArrowDropDownIcon />
                  <FormattedMessage id="subscriptionpage.notificationError" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell />
              <StyledTableCell>
                <Typography variant="h5">
                  <ArrowDropDownIcon />
                  <FormattedMessage id="subscriptionpage.unsubscribe" />
                </Typography>
              </StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {subscriptionStore?.getSubscriptions().map(subscription => (
              <StyledTableRow hover key={subscription.thing.name.name}>
                <StyledTableCell component="th" scope="row">
                  <Typography variant="h5">{subscription.thing.name.name}</Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Typography variant="body1">
                    <FormattedMessage id="subscriptionpage.logMessage1" />
                    {subscription.notificationLevel.days}
                    <FormattedMessage id="subscriptionpage.logMessage2" />
                  </Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Checkbox
                    checked={subscription.directNotification}
                    disabled
                    color="primary"
                    inputProps={{'aria-label': 'secondary checkbox'}}
                  />
                </StyledTableCell>
                <StyledTableCell>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => history.push(`/subscriptions/subscriptionChange/${subscription.id.toString()}`)}
                  >
                    <FormattedMessage id="subscriptionpage.changeButton" />
                  </Button>
                </StyledTableCell>
                <StyledTableCell>
                  <Button variant="contained" color="secondary" onClick={() => handleClickOpen(subscription)}>
                    <FormattedMessage id="subscriptionpage.deleteButton" />
                  </Button>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          <FormattedMessage id="subscriptionpage.unsubscribeMessage" />
        </DialogTitle>
        <DialogActions>
          <Button onClick={handleClose} color="primary">
            <FormattedMessage id="subscriptionpage.disagreeButton" />
          </Button>
          <Button onClick={handleDelete} autoFocus>
            <FormattedMessage id="subscriptionpage.agreeButton" />
          </Button>
        </DialogActions>
      </Dialog>
    </>
  )
}

export default SubscriptionList
