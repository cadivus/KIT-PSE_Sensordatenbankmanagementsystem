import React, {useState} from 'react'
import {createStyles} from '@material-ui/core/styles'
import {Button, Container, Grid, makeStyles, Paper, TextField, Theme, Typography} from '@material-ui/core'
import {FormattedMessage} from 'react-intl'
import Datastream from '../../material/Datastream'
import {getCsvDownloadUrl} from '../../store/communication/backendUrlCreator'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const dateFormat = require('dateformat')

const toMaterialDate = (date: Date) => `${dateFormat(date, 'yyyy-mm-dd')}T${dateFormat(date, 'HH:MM')}`
const toFileDate = (date: Date) => dateFormat(date, 'yyyy-mm-dd')

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
      width: 240,
    },
  }),
)

/**
 *  Displays the export options for thing data.
 *  This class implements a React component.
 */
const Export = ({datastream}: {datastream: Datastream}) => {
  const classes = useStyles()

  const [startDate, setStartDateState] = useState<Date>(new Date())
  const [endDate, setEndDateState] = useState<Date>(new Date())
  const [downloadLink, setDownloadLink] = useState(getCsvDownloadUrl(datastream.datastreamId, startDate, endDate))
  const [downloadName, setDownloadName] = useState(
    `${datastream.datastreamId.toString()}__${toFileDate(startDate)}_to_${toFileDate(endDate)}.csv`,
  )

  const setStartDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setStartDateState(newDate)
    setDownloadLink(getCsvDownloadUrl(datastream.datastreamId, newDate, endDate))
    setDownloadName(`${datastream.datastreamId.toString()}__${toFileDate(newDate)}_to_${toFileDate(endDate)}.csv`)
  }

  const setEndDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setEndDateState(newDate)
    setDownloadLink(getCsvDownloadUrl(datastream.datastreamId, startDate, newDate))
    setDownloadName(`${datastream.datastreamId.toString()}__${toFileDate(startDate)}_to_${toFileDate(newDate)}.csv`)
  }

  return (
    <div className={classes.root}>
      <Paper>
        <Grid container spacing={3}>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <a href={downloadLink} download={downloadName}>
                <Button data-testid="export-button" variant="outlined">
                  <Typography variant="h5">
                    <FormattedMessage id="infopage.export" />
                  </Typography>
                </Button>
              </a>
            </Container>
          </Grid>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <TextField
                inputProps={{'data-testid': 'start-field'}}
                id="datetime-start"
                label="Start point"
                type="datetime-local"
                defaultValue={toMaterialDate(startDate)}
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
                onChange={setStartDate}
              />
            </Container>
          </Grid>
          <Grid item xs={4}>
            <Container className={classes.paper}>
              <TextField
                inputProps={{'data-testid': 'end-field'}}
                id="datetime-end"
                label="End point"
                type="datetime-local"
                defaultValue={toMaterialDate(endDate)}
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
                onChange={setEndDate}
              />
            </Container>
          </Grid>
        </Grid>
      </Paper>
    </div>
  )
}

export default Export
