import React, {FC, useState} from 'react'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import DatastreamList from './DatastreamList'
import Sensor from '../../material/Sensor'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  buttons: {
    paddingTop: '5px',
  },
})

/**
 *  Displays the start page.
 *  This class implements a React component.
 */
const DatastreamView: FC = () => {
  const classes = useStyles()

  const [selectedSensors] = useState(new Set<Sensor>())

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Typography variant="h3" align="center" gutterBottom>
          Datastreams zum Thing: Thingname
        </Typography>
        <DatastreamList selectedSensors={selectedSensors} />
      </Container>
    </div>
  )
}

export default DatastreamView
