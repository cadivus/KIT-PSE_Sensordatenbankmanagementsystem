import React, {useState} from 'react'
import {FormattedMessage} from 'react-intl'
import {
  Button,
  Paper,
  Slider,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  TextField,
  Theme,
  Typography,
  withStyles,
} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import ReplaySpeed from '../../types/ReplaySpeed'
import Thing from '../../types/Thing'
import useReplayStore from '../../hooks/UseReplayStore'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const dateFormat = require('dateformat')

const toMaterialDate = (date: Date) => `${dateFormat(date, 'yyyy-mm-dd')}T${dateFormat(date, 'HH:MM')}`

const StyledTableCell = withStyles((theme: Theme) =>
  createStyles({
    head: {
      backgroundColor: theme.palette.common.white,
      color: theme.palette.common.black,
      fontSize: 17,
    },
    body: {
      fontSize: 14,
    },
  }),
)(TableCell)

const StyledTableRow = withStyles((theme: Theme) =>
  createStyles({
    root: {
      backgroundColor: theme.palette.common.white,
    },
  }),
)(TableRow)

const useStyles = makeStyles({
  thingCell: {
    width: '83%',
  },
  textField: {
    width: 240,
  },
  topMargin: {
    marginTop: '12.2%',
  },
  Margins: {
    marginTop: '3%',
  },
})

/**
 *  Displays the settings of the current replay.
 *  This class implements a React component.
 */
// eslint-disable-next-line @typescript-eslint/no-explicit-any
const ReplaySettings = ({setReplay, things}: {setReplay: any; things: Set<Thing>}) => {
  const classes = useStyles()
  const replayStore = useReplayStore()

  const [startDate, setStartDateState] = useState<Date>((d => new Date(d.setDate(d.getDate() - 3)))(new Date()))
  const [endDate, setEndDateState] = useState<Date>((d => new Date(d.setDate(d.getDate() - 1)))(new Date()))
  const [replaySpeed, setReplaySpeedState] = useState(new ReplaySpeed(1))

  const setStartDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setStartDateState(newDate)
  }

  const setEndDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setEndDateState(newDate)
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const setReplaySpeed = (event: any, newValue: number | number[]): void => {
    const newSpeed = new ReplaySpeed(Number(newValue))
    setReplaySpeedState(newSpeed)
  }

  const createReplay = (): void => {
    replayStore?.createReplay(startDate, endDate, replaySpeed, things).then(newReplay => {
      setReplay(newReplay)
    })
  }

  return (
    <div>
      <TableContainer component={Paper} className={classes.topMargin}>
        <Table>
          <TableBody>
            <StyledTableRow>
              <StyledTableCell component="th" scope="row">
                <Typography data-testid="start-text" variant="body1">
                  <FormattedMessage id="replaypage.start" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <TextField
                  inputProps={{'data-testid': 'startDate-field'}}
                  id="datetime-start"
                  label="Next appointment"
                  type="datetime-local"
                  defaultValue={toMaterialDate(startDate)}
                  className={classes.textField}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  onChange={setStartDate}
                />
              </StyledTableCell>
            </StyledTableRow>
            <StyledTableRow>
              <StyledTableCell component="th" scope="row">
                <Typography data-testid="stop-text" variant="body1">
                  <FormattedMessage id="replaypage.stop" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <TextField
                  inputProps={{'data-testid': 'stopDate-field'}}
                  id="datetime-end"
                  label="Next appointment"
                  type="datetime-local"
                  defaultValue={toMaterialDate(endDate)}
                  className={classes.textField}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  onChange={setEndDate}
                />
              </StyledTableCell>
            </StyledTableRow>
            <StyledTableRow>
              <StyledTableCell component="th" scope="row">
                <Typography data-testid="speed-text" variant="body1">
                  <FormattedMessage id="replaypage.speed" />
                </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Slider
                  inputProps={{'data-testid': 'speed-slider'}}
                  defaultValue={replaySpeed.toNumber()}
                  min={1}
                  max={1000}
                  onChange={setReplaySpeed}
                />
              </StyledTableCell>
              <StyledTableCell>
                <Typography data-testid="speed-value" variant="body1">{`${replaySpeed.toString()}x`}</Typography>
              </StyledTableCell>
            </StyledTableRow>
          </TableBody>
        </Table>
      </TableContainer>
      <Button data-testid="play-button" variant="outlined" className={classes.Margins} onClick={createReplay}>
        <FormattedMessage id="replaypage.play" />
      </Button>
    </div>
  )
}

export default ReplaySettings
