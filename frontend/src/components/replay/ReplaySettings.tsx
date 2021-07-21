import React from 'react'
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
  sensorCell: {
    width: '83%',
  },
  textField: {
    width: 130,
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

  return (
    <TableContainer component={Paper} className={classes.topMargin}>
      <Table>
        <TableBody>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="body1">Start:</Typography>
            </StyledTableCell>
            <StyledTableCell>
              <TextField
                id="date"
                type="date"
                defaultValue="2017-05-24"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </StyledTableCell>
            <StyledTableCell>
              <TextField
                id="time"
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
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="body1">Stop:</Typography>
            </StyledTableCell>
            <StyledTableCell>
              <TextField
                id="date"
                type="date"
                defaultValue="2017-05-24"
                className={classes.textField}
                InputLabelProps={{
                  shrink: true,
                }}
              />
            </StyledTableCell>
            <StyledTableCell>
              <TextField
                id="time"
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
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="body1">Speed:</Typography>
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
