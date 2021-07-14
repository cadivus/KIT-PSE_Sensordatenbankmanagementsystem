// http://localhost:3000/replay/replaySingleView

import React from 'react'
import {Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import ReplaySettings from './ReplaySettings'
import ReplayRequest from './ReplayRequest'
import ReplayHelp from './ReplayHelp'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  root: {
    flexGrow: 1,
  },
})

/**
 *  Displays the webpage for a replay with a single sensor.
 *  This class implements a React component.
 */
const ReplaySingleView = () => {
  const classes = useStyles()

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={6}>
            <Typography variant="h3" gutterBottom>
              Sensorname
            </Typography>
          </Grid>
          <Grid item xs={6}>
            <ReplaySettings />
            <ReplayRequest />
            <ReplayHelp />
          </Grid>
        </Grid>
      </Container>
    </div>
  )
}

export default ReplaySingleView
