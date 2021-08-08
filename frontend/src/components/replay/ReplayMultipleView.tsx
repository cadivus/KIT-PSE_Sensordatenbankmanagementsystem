// http://localhost:3000/replay/replayMultipleView

import React from 'react'
import {Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import ReplaySettings from './ReplaySettings'
import ReplayRequest from './ReplayRequest'
import ReplayHelp from './ReplayHelp'
import ReplayThingList from './ReplayThingList'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  root: {
    flexGrow: 1,
  },
})
/**
 *  Displays the webpage of the replay with multiple things.
 *  This class implements a React component.
 */
const ReplayMultipleView = () => {
  const classes = useStyles()

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={4}>
          <Grid item xs={6}>
            <Typography variant="h3" gutterBottom>
              2020-12-12 10:10:10
            </Typography>
            <ReplayThingList />
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

export default ReplayMultipleView
