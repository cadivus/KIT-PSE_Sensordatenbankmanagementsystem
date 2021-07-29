import React, {FC, useState} from 'react'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import {useHistory} from 'react-router-dom'
import {FormattedMessage} from 'react-intl'
import SensorList from './SensorList'
import Search from './Search'
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
const StartpageView: FC = () => {
  const history = useHistory()
  const classes = useStyles()

  const [selectedSensors] = useState(new Set<Sensor>())

  const subscribeClicked = () => {
    history.push({
      pathname: '/subscriptions/subscriptionCreate',
      // eslint-disable-next-line object-shorthand
      state: {selectedSensors: selectedSensors},
    })
  }

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Typography variant="h3" align="center" gutterBottom>
          <FormattedMessage id="startpage.startpage" />
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={8}>
            <Search />
          </Grid>
          <Grid item xs={2}>
            <Container className={classes.buttons}>
              <Button variant="outlined" onClick={() => subscribeClicked()}>
                <FormattedMessage id="startpage.subscribe" />
              </Button>
            </Container>
          </Grid>
          <Grid item xs={2}>
            <Container className={classes.buttons}>
              <Button variant="outlined" onClick={() => history.push('/replay/replayMultipleView')}>
                <FormattedMessage id="startpage.replay" />
              </Button>
            </Container>
          </Grid>
        </Grid>
        <SensorList selectedSensors={selectedSensors} />
      </Container>
    </div>
  )
}

export default StartpageView
