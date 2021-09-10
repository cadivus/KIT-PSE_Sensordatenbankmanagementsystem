import React, {FC, useEffect, useState} from 'react'
import {Redirect, useHistory, useParams} from 'react-router-dom'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import {FormattedMessage} from 'react-intl'
import Properties from './Properties'
import useThingStore from '../../hooks/UseThingStore'
import Id from '../../types/Id'
import Thing from '../../types/Thing'
import DatastreamList from './DatastreamList'
import Loading from '../Loading'
import ThingTitle from './ThingTitle'

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

  if (loading) return <Loading data-testid="loadingPage" />
  if (!thing) return <ErrorHandling />

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <ThingTitle thing={thing} />
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
