import React from 'react'
import {Container, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import SensorList from './SensorList'
import Search from './Search'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
})

/**
 *  Displays the start page.
 *  This class implements a React component.
 */
const StartpageView = () => {
  const classes = useStyles()
  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Typography variant="h3" align="center" gutterBottom>
          Startpage
        </Typography>
        <Search />
        <SensorList />
      </Container>
    </div>
  )
}

export default StartpageView
