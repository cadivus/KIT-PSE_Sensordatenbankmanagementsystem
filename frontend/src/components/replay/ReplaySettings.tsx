import React, {useState} from 'react'
import {FormattedMessage} from 'react-intl'
import {
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
})

/**
 *  Displays the settings of the current replay.
 *  This class implements a React component.
 */
const ReplaySettings = () => {
  const classes = useStyles()

  const [startDate, setStartDateState] = useState<Date>(new Date())
  const [endDate, setEndDateState] = useState<Date>(new Date())

  const setStartDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setStartDateState(newDate)
  }

  const setEndDate = (e: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>): void => {
    const newDate = new Date(e.target.value)
    setEndDateState(newDate)
  }

  return (
    <TableContainer component={Paper} className={classes.topMargin}>
      <Table>
        <TableBody>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="body1">
                <FormattedMessage id="replaypage.start" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <TextField
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
              <Typography variant="body1">
                <FormattedMessage id="replaypage.stop" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <TextField
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
              <Typography variant="body1">
                <FormattedMessage id="replaypage.speed" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Slider defaultValue={0} />
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="body1">50x</Typography>
            </StyledTableCell>
          </StyledTableRow>
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default ReplaySettings
