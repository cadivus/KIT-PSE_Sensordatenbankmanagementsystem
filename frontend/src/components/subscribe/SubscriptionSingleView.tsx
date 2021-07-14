// http://localhost:3000/subscriptions/subscriptionSingleView

import React from 'react'
import {Button, Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import SubscriptionSettings from './SubscriptionSettings'

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

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Typography variant="h3" gutterBottom>
        Sensorname
      </Typography>
      <SubscriptionSettings />
      <Button variant="outlined">
        <Typography variant="body1">Subscribe</Typography>
      </Button>
    </Container>
  )
}

export default SubscriptionSingleView
