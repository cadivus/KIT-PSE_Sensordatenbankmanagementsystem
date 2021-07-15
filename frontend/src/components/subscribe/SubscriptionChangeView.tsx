// http://localhost:3000/subscriptions/subscriptionChangeView

import React from 'react'
import {useParams} from 'react-router-dom'
import {Button, Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import SubscriptionSettings from './SubscriptionSettings'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import Id from '../../material/Id'

const useStyles = makeStyles({
  container: {
    marginTop: '2%',
  },
})

/**
 * Displays the view for changing subscriptions.
 * This class implements a React component.
 */
const SubscriptionChangeView = () => {
  const classes = useStyles()
  const {subscriptionId} = useParams<{subscriptionId: string}>()
  const subscriptionStore = useSubscriptionStore()
  const subscription = subscriptionStore?.getSubscription(new Id(subscriptionId))

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Typography variant="h3" gutterBottom>
        {subscription?.sensors[0].name.name}
      </Typography>
      <SubscriptionSettings />
      <Button variant="outlined">
        <Typography variant="body1">Change</Typography>
      </Button>
    </Container>
  )
}

export default SubscriptionChangeView
