// http://localhost:3000/subscriptions/subscriptionChangeView

import React, {useState} from 'react'
import {useParams} from 'react-router-dom'
import {Button, Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import SubscriptionSettings from './SubscriptionSettings'
import useSubscriptionStore from '../../hooks/UseSubscriptionStore'
import Id from '../../material/Id'
import NotificationLevel from '../../material/NotificationLevel'

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

  const [directNotification, setDirectNotification] = useState(subscription?.directNotification)
  const [notificationLevel, setNotificationLevel] = useState(subscription?.notificationLevel)

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Typography variant="h3" gutterBottom>
        {subscription?.sensors[0].name.name}
      </Typography>
      <SubscriptionSettings
        directNotification={directNotification}
        setDirectNotification={setDirectNotification}
        notificationLevel={notificationLevel}
        setNotificationLevel={setNotificationLevel}
      />
      <Button variant="outlined">
        <Typography variant="body1">Change</Typography>
      </Button>
    </Container>
  )
}

export default SubscriptionChangeView
