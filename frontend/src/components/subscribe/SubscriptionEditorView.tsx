import React, {useState} from 'react'
import {useParams, Redirect, useHistory} from 'react-router-dom'
import {FormattedMessage} from 'react-intl'
import {
  Button,
  Container,
  Grid,
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
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import SubscriptionSettings from './SubscriptionSettings'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import Id from '../../types/Id'
import Thing from '../../types/Thing'
import NotificationLevel from '../../types/NotificationLevel'

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

const useStyles = makeStyles({
  container: {
    marginTop: '2%',
  },
  marginTop: {
    marginTop: '15px',
  },
})

const ThingsList = ({things}: {things: Array<Thing>}) => {
  return (
    <TableContainer component={Paper}>
      <Table data-testid="thing-table">
        <TableHead>
          <TableRow>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="subscriptionpage.thing" />
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {things.map(thing => (
            <TableRow hover key={thing.name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h5">{thing.name.name}</Typography>
              </StyledTableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

const ErrorHandling = () => {
  return <Redirect to="/" />
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const checkProps = (props: any) => {
  if (!props?.location?.state?.selectedThings) return false
  if (props.location.state.selectedThings.size === 0) return false

  return true
}

/**
 * Displays the webpage of the multiple subscription
 * This class implements a React component.
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const SubscriptionEditorView = (props: any) => {
  const classes = useStyles()
  const history = useHistory()
  const {subscriptionId} = useParams<{subscriptionId: string}>()
  const subscriptionStore = useSubscriptionStore()
  const subscription = subscriptionStore?.getSubscription(new Id(subscriptionId))

  if (!subscription && !checkProps(props)) {
    return <ErrorHandling />
  }
  const createMode = !subscription

  const [directNotification, setDirectNotification] = createMode
    ? useState(false)
    : useState(subscription?.directNotification)
  const [notificationLevel, setNotificationLevel] = createMode
    ? useState(new NotificationLevel(5, true))
    : useState(subscription?.notificationLevel)

  const things = new Array<Thing>()
  if (createMode) {
    props.location.state.selectedThings.forEach((e: Thing) => {
      things.push(e)
    })
  }

  const multipleThings = things && things.length > 1

  const updateSubscription = createMode
    ? () => {
        if (!subscriptionStore || !notificationLevel || !(typeof directNotification === 'boolean')) return
        subscriptionStore.createSubscriptions(things, directNotification, notificationLevel)
        history.push('/subscriptions')
      }
    : () => {
        if (!subscription) return
        if (notificationLevel) subscription.notificationLevel = notificationLevel
        if (typeof directNotification === 'boolean') subscription.directNotification = directNotification
        history.push('/subscriptions')
      }

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Grid container spacing={3}>
        {multipleThings && things ? (
          <Grid item xs={5}>
            <ThingsList things={things} />
          </Grid>
        ) : (
          <></>
        )}
        <Grid item xs={multipleThings ? 7 : 12}>
          <SubscriptionSettings
            directNotification={directNotification}
            setDirectNotification={setDirectNotification}
            notificationLevel={notificationLevel}
            setNotificationLevel={setNotificationLevel}
          />
          <Button
            data-testid="subscribe-change-button"
            variant="outlined"
            onClick={() => updateSubscription()}
            className={classes.marginTop}
          >
            <Typography variant="body1">{createMode ? 'Subscribe' : 'Change'}</Typography>
          </Button>
        </Grid>
      </Grid>
    </Container>
  )
}

export default SubscriptionEditorView
