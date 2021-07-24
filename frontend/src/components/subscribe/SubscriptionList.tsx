import React, {FC} from 'react'
import {
  Button,
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
} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import {useHistory} from 'react-router-dom'
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import Checkbox from '@material-ui/core/Checkbox'
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
// const SubscriptionList: FC = ({selectedSubscriptions}: {selectedSubscriptions: Set<Subscription>}) => {
const SubscriptionList: FC = () => {
  const classes = useStyles()
  const history = useHistory()
  const subscriptionStore = useSubscriptionStore()

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Sensor
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Log level
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Notify on Error
              </Typography>
            </StyledTableCell>
            <StyledTableCell />
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Unsubscribe
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {subscriptionStore?.getSubscriptions().map(subscription => (
            <StyledTableRow hover key={subscription.sensors[0].name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h5">{subscription.sensors[0].name.name}</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="body1">Every {subscription.notificationLevel.days} days</Typography>
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
                  change
                </Button>
              </StyledTableCell>
              <StyledTableCell>
                <Checkbox color="primary" inputProps={{'aria-label': 'secondary checkbox'}} />
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default SubscriptionList
