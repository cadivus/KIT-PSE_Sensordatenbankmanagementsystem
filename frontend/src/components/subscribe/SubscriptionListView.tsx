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
      <Container data-testid="subscriptionListView-Container" maxWidth="lg" className={classes.container}>
        <SubscriptionList />
      </Container>
    </div>
  )
}

export default SubscriptionListView
