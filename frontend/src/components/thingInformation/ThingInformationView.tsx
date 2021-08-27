import React, {FC, useEffect, useState} from 'react'
import {Redirect, useHistory, useParams} from 'react-router-dom'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {FormattedMessage} from 'react-intl'
import {green, red, grey} from '@material-ui/core/colors'
import Properties from './Properties'
import useThingStore from '../../hooks/UseThingStore'
import Id from '../../material/Id'
import Thing, {ThingState} from '../../material/Thing'
import DatastreamList from './DatastreamList'
import Loading from '../Loading'

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

  const [loading, setLoading] = useState(true)
  const [thing, setThing] = useState<undefined | Thing>(undefined)
  useEffect(() => {
    if (thingStore && !thing) {
      thingStore
        .getThing(new Id(thingId))
        .then(newThing => {
          setThing(newThing)
          setLoading(false)
        })
        .catch(() => {
          setLoading(false)
        })
    }
  }, [thingStore, setThing, setLoading, thingId, thing])

  const onSubscribeClick = () => {
    if (!thing) return
    const selectedThings = new Set<Thing>()
    selectedThings.add(thing)
    history.push({
      pathname: '/subscriptions/subscriptionCreate',
      // eslint-disable-next-line object-shorthand
      state: {selectedThings: selectedThings},
    })
  }

  const onReplayClick = () => {
    if (!thing) return
    const selectedThings = new Set<Thing>()
    selectedThings.add(thing)
    history.push({
      pathname: '/replay',
      // eslint-disable-next-line object-shorthand
      state: {selectedThings: selectedThings},
    })
  }

  const [activeState, setActiveState] = useState(ThingState.Unknown)

  useEffect(() => {
    const interval = setInterval(() => {
      if (!thing) return
      thing.isActive().then(state => {
        setActiveState(state)
      })
    }, 10000)

    return () => {
      clearInterval(interval)
    }
  })

  if (loading) return <Loading />

  if (!thing) return <ErrorHandling />

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
          <Grid item xs={8}>
            {thing ? <Properties thing={thing} /> : <></>}
          </Grid>
          <Grid item xs={4}>
            <Grid container spacing={3}>
              <Grid item xs={12} className={classes.buttonspacing}>
                <Button variant="outlined" className={classes.button} onClick={onReplayClick}>
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
            <DatastreamList thing={thing} />
          </Grid>
        </Grid>
      </Container>
    </div>
  )
}

export default ThingInformationView
