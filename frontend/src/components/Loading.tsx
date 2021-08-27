import React from 'react'
import {makeStyles} from '@material-ui/core/styles'
import {Paper, Container, CircularProgress, Grid} from '@material-ui/core'

const useStyles = makeStyles({
  paper: {
    marginTop: '10%',
    marginLeft: '2%',
    marginRight: '2%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  container: {
    marginTop: '20px',
    marginBottom: '20px',
    marginLeft: '20px',
    marginRight: '20px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  grid: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  progress: {
    alignItems: 'center',
    display: 'flex',
    justifyContent: 'center',
  },
})

const Loading = () => {
  const classes = useStyles()

  return (
    <div>
      <Paper className={classes.paper}>
        <Container className={classes.container}>
          <Grid container spacing={3} className={classes.grid}>
            <Grid item xs={12} className={classes.grid} />
            <Grid item xs={12} className={classes.grid}>
              <CircularProgress className={classes.progress} />
            </Grid>
            <Grid item xs={12} className={classes.grid}>
              <p>Loading...</p>
            </Grid>
          </Grid>
        </Container>
      </Paper>
    </div>
  )
}

export default Loading
