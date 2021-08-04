import React from 'react'
import {createStyles} from '@material-ui/core/styles'
import {Button, Container, Grid, makeStyles, Paper, TextField, Theme, Typography} from '@material-ui/core'
import {FormattedMessage} from 'react-intl'

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      flexGrow: 1,
    },
    paper: {
      marginLeft: '10px',
      marginRight: '10px',
      padding: theme.spacing(2),
      textAlign: 'center',
    },
    textField: {
      marginLeft: theme.spacing(1),
      marginRight: theme.spacing(1),
      width: 130,
    },
  }),
)

/**
 *  Displays the export options for sensor data.
 *  This class implements a React component.
 */
const Export = () => {
  const classes = useStyles()

  return (
    <div className={classes.root}>
      <Paper>
        <Grid container spacing={3}>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <Button variant="outlined">
                <Typography variant="h5">
                  <FormattedMessage id="infopage.export" />
                </Typography>
              </Button>
            </Container>
          </Grid>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <TextField
                id="date"
                label={<FormattedMessage id="infopage.fromDate" />}
                type="date"
                defaultValue="2017-05-24"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
              />
              <TextField
                id="time"
                label={<FormattedMessage id="infopage.fromTime" />}
                type="time"
                defaultValue="07:30"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
                inputProps={{
                  step: 300, // 5 min
                }}
              />
            </Container>
          </Grid>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <TextField
                id="date"
                label={<FormattedMessage id="infopage.toDate" />}
                type="date"
                defaultValue="2017-05-24"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
              />
              <TextField
                id="time"
                label={<FormattedMessage id="infopage.toTime" />}
                type="time"
                defaultValue="07:30"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
                inputProps={{
                  step: 300, // 5 min
                }}
              />
            </Container>
          </Grid>
        </Grid>
      </Paper>
    </div>
  )
}

export default Export
