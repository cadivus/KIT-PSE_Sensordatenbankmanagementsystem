// http://localhost:3000/subscriptions/subscriptionChangeView

import React, {useState} from 'react'
import {useParams, Redirect, useHistory} from 'react-router-dom'
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
import {FormattedMessage} from 'react-intl'
import SubscriptionSettings from './SubscriptionSettings'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import Id from '../../material/Id'
import Thing from '../../material/Thing'
import NotificationLevel from '../../material/NotificationLevel'

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
      <Table>
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
            <StyledTableRow hover key={thing.name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h5">{thing.name.name}</Typography>
              </StyledTableCell>
            </StyledTableRow>
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
  if (!props) return false
  if (!props.location) return false
  if (!props.location.state) return false
  if (!props.location.state.selectedThings) return false
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

  const things = subscription ? subscription.things : new Array<Thing>()
  if (createMode) {
    props.location.state.selectedThings.forEach((e: Thing) => {
      things.push(e)
    })
  }

  const multipleThings = things && things.length > 1

  const updateSubscription = createMode
    ? () => {
        if (!subscriptionStore || !notificationLevel || !(typeof directNotification === 'boolean')) return
        console.log('ah')
        subscriptionStore.createSubscription(things, directNotification, notificationLevel)
        console.log('ah')
        history.push('/subscriptions')
        console.log('ah')
      }
    : () => {
        if (!subscription) return
        if (notificationLevel) subscription.notificationLevel = notificationLevel
        if (typeof directNotification === 'boolean') subscription.directNotification = directNotification
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
          <Button variant="outlined" onClick={() => updateSubscription()} className={classes.marginTop}>
            <Typography variant="body1">{createMode ? 'Subscribe' : 'Change'}</Typography>
          </Button>
        </Grid>
      </Grid>
    </Container>
  )
}

export default SubscriptionEditorView
