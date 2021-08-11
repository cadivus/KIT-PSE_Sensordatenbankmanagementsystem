import React, {useEffect, useState} from 'react'
import {Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import ReplaySettings from './ReplaySettings'
import ReplayRequest from './ReplayRequest'
import ReplayHelp from './ReplayHelp'
import ReplayThingList from './ReplayThingList'
import Thing from '../../material/Thing'

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
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const ReplayMultipleView = (props: any) => {
  const classes = useStyles()

  const [things, setThings] = useState(new Set<Thing>())

  useEffect(() => {
    const newSelected = new Set<Thing>()
    props.location.state.selectedThings.forEach((e: Thing) => {
      newSelected.add(e)
    })
    setThings(newSelected)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Grid container spacing={4}>
          <Grid item xs={6}>
            <ReplayThingList things={things} />
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
