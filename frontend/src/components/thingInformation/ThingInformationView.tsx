import React, {FC, useEffect, useState} from 'react'
import {Redirect, useHistory, useParams} from 'react-router-dom'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {FormattedMessage} from 'react-intl'
import {green, red, grey} from '@material-ui/core/colors'
import Properties from './Properties'
import Export from './Export'
import Data from './Data'
import useThingStore from '../../hooks/UseThingStore'
import Id from '../../material/Id'
import Thing, {ThingState} from '../../material/Thing'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  root: {
    flexGrow: 1,
  },
  buttonspacing: {
    marginTop: '100px',
  },
  button: {
    width: '200px',
    marginLeft: '200px',
  },
})

const ErrorHandling = () => {
  return <Redirect to="/" />
}

/**
 *  Displays the thing information page.
 *  This class implements a React component.
 */
const ThingInformationView: FC = () => {
  const history = useHistory()
  const classes = useStyles()
  const thingStore = useThingStore()
  const {thingId} = useParams<{thingId: string}>()
  const thing = thingStore?.getThing(new Id(thingId))
  if (!thing) {
    return <ErrorHandling />
  }

  const onSubscribeClick = () => {
    const selectedThings = new Set<Thing>()
    selectedThings.add(thing)
    history.push({
      pathname: '/subscriptions/subscriptionCreate',
      // eslint-disable-next-line object-shorthand
      state: {selectedThings: selectedThings},
    })
  }

  const [activeState, setActiveState] = useState(thing.isActive())

  useEffect(() => {
    const interval = setInterval(() => {
      const active = thing.isActive()
      if (activeState !== active) setActiveState(active)
    }, 2000)

    return () => {
      clearInterval(interval)
    }
  })

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Typography variant="h3" align="center" gutterBottom>
              {thing.name.toString()}
              {activeState === ThingState.Online ? (
                <FiberManualRecordIcon style={{color: green[500]}} fontSize="large" />
              ) : activeState === ThingState.Offline ? (
                <FiberManualRecordIcon style={{color: red[500]}} fontSize="large" />
              ) : (
                <FiberManualRecordIcon style={{color: grey[500]}} fontSize="large" /> /* Unknown state */
              )}
            </Typography>
          </Grid>
          <Grid item xs={6}>
            {thing ? <Properties thing={thing} /> : <></>}
          </Grid>
          <Grid item xs={6}>
            <Grid container spacing={3}>
              <Grid item xs={12} className={classes.buttonspacing}>
                <Button
                  variant="outlined"
                  className={classes.button}
                  onClick={() => history.push('/replay/replaySingleView')}
                >
                  <Typography variant="h5">
                    <FormattedMessage id="infopage.replay" />
                  </Typography>
                </Button>
              </Grid>
              <Grid item xs={12}>
                <Button variant="outlined" className={classes.button} onClick={onSubscribeClick}>
                  <Typography variant="h5">
                    <FormattedMessage id="infopage.subscribe" />
                  </Typography>
                </Button>
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Export />
          </Grid>
          <Grid item xs={12}>
            <Data />
          </Grid>
        </Grid>
      </Container>
    </div>
  )
}

export default ThingInformationView
