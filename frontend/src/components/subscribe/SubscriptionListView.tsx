import React, {FC} from 'react'
import {
  Button,
  Container,
  Dialog,
  DialogActions,
  DialogTitle,
  TableCell,
  TableRow,
  Theme,
  withStyles,
} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import {useHistory} from 'react-router-dom'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import SubscriptionList from './SubscriptionList'

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
const SubscriptionListView: FC = () => {
  const classes = useStyles()

  return (
    <div className={classes.root}>
      <Container maxWidth="lg" className={classes.container}>
        <SubscriptionList />
      </Container>
    </div>
  )
}

export default SubscriptionListView
