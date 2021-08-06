import React, {FC, useEffect, useState} from 'react'
import {Redirect, useHistory, useParams} from 'react-router-dom'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {FormattedMessage} from 'react-intl'
import {green} from '@material-ui/core/colors'
import Properties from './Properties'
import Export from './Export'
import Data from './Data'
import useSensorStore from '../../hooks/UseSensorStore'
import Id from '../../material/Id'
import Sensor, {SensorState} from '../../material/Sensor'

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
 *  Displays the sensor information page.
 *  This class implements a React component.
 */
const SensorInformationView: FC = () => {
  const history = useHistory()
  const classes = useStyles()
  const sensorStore = useSensorStore()
  const {sensorId} = useParams<{sensorId: string}>()
  const sensor = sensorStore?.getSensor(new Id(sensorId))
  if (!sensor) {
    return <ErrorHandling />
  }

  const onSubscribeClick = () => {
    const selectedSensors = new Set<Sensor>()
    selectedSensors.add(sensor)
    history.push({
      pathname: '/subscriptions/subscriptionCreate',
      // eslint-disable-next-line object-shorthand
      state: {selectedSensors: selectedSensors},
    })
  }

  const [activeState, setActiveState] = useState(sensor.isActive())

  useEffect(() => {
    const interval = setInterval(() => {
      const active = sensor.isActive()
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
              {sensor.name.toString()}
              {activeState === SensorState.Online ? (
                <FiberManualRecordIcon style={{color: green[500]}} fontSize="large" />
              ) : activeState === SensorState.Offline ? (
                <FiberManualRecordIcon color="disabled" fontSize="large" />
              ) : (
                /* Unknown state */ <FiberManualRecordIcon color="secondary" fontSize="large" />
              )}
            </Typography>
          </Grid>
          <Grid item xs={6}>
            {sensor ? <Properties sensor={sensor} /> : <></>}
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

export default SensorInformationView
