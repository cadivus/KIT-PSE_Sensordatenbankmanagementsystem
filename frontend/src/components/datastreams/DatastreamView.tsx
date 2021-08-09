import React, {FC, useState} from 'react'
import {Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import DatastreamList from './DatastreamList'
import Thing from '../../material/Thing'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  buttons: {
    paddingTop: '5px',
  },
})

/**
 *  Displays a list of datastreams.
 */
const DatastreamView: FC = () => {
  const classes = useStyles()

  const [selectedThings] = useState(new Set<Thing>())

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Typography variant="h3" align="center" gutterBottom>
          Datastreams zum Thing: Thingname
        </Typography>
        <DatastreamList selectedThings={selectedThings} />
      </Container>
    </div>
  )
}

export default DatastreamView
