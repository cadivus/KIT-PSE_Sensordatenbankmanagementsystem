// http://localhost:3000/sensorInformation

import React from 'react'
import {Button, Container, Grid, Theme, Typography} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import Properties from './Properties'
import Export from './Export'

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      marginTop: '15px',
    },
    root: {
      flexGrow: 1,
    },
    paper: {
      padding: theme.spacing(2),
      textAlign: 'center',
      color: theme.palette.text.secondary,
    },
    buttonspacing: {
      marginTop: '100px',
    },
    button: {
      width: '200px',
      marginLeft: '200px',
    },
  }),
)

/**
 *  Displays the sensor information page.
 *  This class implements a React component.
 */
const SensorInformationView = () => {
  const classes = useStyles()

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={3}>
          <Grid item xs={12}>
            <Typography variant="h3" align="center" gutterBottom>
              Sensorname <FiberManualRecordIcon color="secondary" fontSize="large" />
            </Typography>
          </Grid>
          <Grid item xs={6}>
            <Properties />
          </Grid>
          <Grid item xs={6}>
            <Grid container spacing={3}>
              <Grid item xs={12} className={classes.buttonspacing}>
                <Button variant="outlined" className={classes.button}>
                  <Typography variant="h5"> Replay </Typography>
                </Button>
              </Grid>
              <Grid item xs={12}>
                <Button variant="outlined" className={classes.button}>
                  <Typography variant="h5"> Subscribe </Typography>
                </Button>
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12}>
            <Export />
          </Grid>
        </Grid>
      </Container>
    </div>
  )
}

export default SensorInformationView
