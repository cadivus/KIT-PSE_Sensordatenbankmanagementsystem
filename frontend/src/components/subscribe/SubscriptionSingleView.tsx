// http://localhost:3000/subscriptions/subscriptionSingleView

import React, {useState} from 'react'
import {Button, Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import SubscriptionSettings from './SubscriptionSettings'
import NotificationLevel from '../../material/NotificationLevel'

const useStyles = makeStyles({
  container: {
    marginTop: '2%',
  },
})

/**
 * Displays the webpage for a single subscription.
 * This class implements a React component.
 */
const SubscriptionSingleView = () => {
  const classes = useStyles()

  const [directNotification, setDirectNotification] = useState(true)
  const [notificationLevel, setNotificationLevel] = useState(new NotificationLevel(666))

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Typography variant="h3" gutterBottom>
        Sensorname
      </Typography>
      <SubscriptionSettings
        directNotification={directNotification}
        setDirectNotification={setDirectNotification}
        notificationLevel={notificationLevel}
        setNotificationLevel={setNotificationLevel}
      />
      <Button variant="outlined">
        <Typography variant="body1">Subscribe</Typography>
      </Button>
    </Container>
  )
}

export default SubscriptionSingleView
