import React, {FC, useEffect, useState} from 'react'
import {Redirect, useHistory, useParams} from 'react-router-dom'
import {Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {green, red, grey} from '@material-ui/core/colors'
import Properties from '../thingInformation/Properties'
import Export from './Export'
import Data from './Data'
import useThingStore from '../../hooks/UseThingStore'
import Id from '../../material/Id'
import {ThingState} from '../../material/Thing'
import useDatastreamStore from '../../hooks/UseDatastreamStore'

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
const DatastreamView: FC = () => {
  const history = useHistory()
  const classes = useStyles()
  const thingStore = useThingStore()
  const datastreamStore = useDatastreamStore()

  const {thingId} = useParams<{thingId: string}>()
  const thing = thingStore?.getThing(new Id(thingId))
  if (!thing) {
    return <ErrorHandling />
  }

  const {datastreamId} = useParams<{datastreamId: string}>()
  const datastream = datastreamStore?.getDatastream(new Id(datastreamId))
  if (!datastream) {
    return <ErrorHandling />
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
