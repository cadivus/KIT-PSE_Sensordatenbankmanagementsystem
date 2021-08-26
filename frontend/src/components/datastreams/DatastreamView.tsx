import React, {FC, useEffect, useState} from 'react'
import {Redirect, useParams} from 'react-router-dom'
import {Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {green, red, grey} from '@material-ui/core/colors'
import Properties from '../thingInformation/Properties'
import Export from './Export'
import Data from './Data'
import useThingStore from '../../hooks/UseThingStore'
import Id from '../../material/Id'
import Thing, {ThingState} from '../../material/Thing'
import useDatastreamStore from '../../hooks/UseDatastreamStore'
import Datastream from '../../material/Datastream'
import ThingStore from '../../store/ThingStore'
import DatastreamStore from '../../store/DatastreamStore'
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

const loadThing = (
  thingStore: ThingStore | undefined,
  setThing: (newValue: Thing) => void,
  setThingLoading: (newValue: boolean) => void,
  thingId: string,
  thing: Thing | undefined,
) => {
  if (thingStore && !thing) {
    thingStore
      .getThing(new Id(thingId))
      .then(newThing => {
        setThing(newThing)
        setThingLoading(false)
      })
      .catch(() => {
        setThingLoading(false)
      })
  }
}

const loadDatastream = (
  datastreamStore: DatastreamStore | undefined,
  setDatastream: (newValue: Datastream) => void,
  setDatastreamLoading: (newValue: boolean) => void,
  datastreamId: string,
  datastream: Datastream | undefined,
) => {
  if (datastreamStore && !datastream) {
    datastreamStore
      .getDatastream(new Id(datastreamId))
      .then(newDatastream => {
        setDatastream(newDatastream)
        setDatastreamLoading(false)
      })
      .catch(() => {
        setDatastreamLoading(false)
      })
  }
}

/**
 *  Displays the thing information page.
 *  This class implements a React component.
 */
const DatastreamView: FC = () => {
  const classes = useStyles()
  const thingStore = useThingStore()
  const datastreamStore = useDatastreamStore()

  const [thingLoading, setThingLoading] = useState(true)
  const [thing, setThing] = useState<undefined | Thing>(undefined)
  const {thingId} = useParams<{thingId: string}>()
  useEffect(() => {
    loadThing(thingStore, setThing, setThingLoading, thingId, thing)
  }, [thingStore, setThing, setThingLoading, thingId, thing])

  const [datastreamLoading, setDatastreamLoading] = useState(true)
  const [datastream, setDatastream] = useState<undefined | Datastream>(undefined)
  const {datastreamId} = useParams<{datastreamId: string}>()
  useEffect(() => {
    loadDatastream(datastreamStore, setDatastream, setDatastreamLoading, datastreamId, datastream)
  }, [datastreamStore, setDatastream, setDatastreamLoading, datastreamId, datastream])

  const [activeState, setActiveState] = useState(ThingState.Unknown)

  useEffect(() => {
    const interval = setInterval(() => {
      if (!thing) return
      const active = thing.isActive()
      if (activeState !== active) setActiveState(active)
    }, 10000)

    return () => {
      clearInterval(interval)
    }
  })

  if (thingLoading || datastreamLoading) return <Loading />
  if (!thing || !datastream) return <ErrorHandling />

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Typography variant="h3" align="center" gutterBottom>
              {datastream.name.toString()}
              {activeState === ThingState.Online ? (
                <FiberManualRecordIcon style={{color: green[500]}} fontSize="large" />
              ) : activeState === ThingState.Offline ? (
                <FiberManualRecordIcon style={{color: red[500]}} fontSize="large" />
              ) : (
                <FiberManualRecordIcon style={{color: grey[500]}} fontSize="large" /> /* Unknown state */
              )}
            </Typography>
          </Grid>
          <Grid item xs={12}>
            {thing ? <Properties thing={thing} /> : <></>}
          </Grid>
          <Grid item xs={12}>
            <Export datastream={datastream} />
          </Grid>
          <Grid item xs={12}>
            <Data datastream={datastream} />
          </Grid>
        </Grid>
      </Container>
    </div>
  )
}

export default DatastreamView
