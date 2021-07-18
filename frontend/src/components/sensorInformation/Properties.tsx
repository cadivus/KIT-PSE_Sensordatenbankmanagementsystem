import React from 'react'
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableRow,
  Theme,
  Typography,
  withStyles,
} from '@material-ui/core'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import Checkbox from '@material-ui/core/Checkbox'
import Sensor from '../../material/Sensor'

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
  table: {
    width: '100%',
  },
})

/**
 *  Displays the properties of a selected sensor.
 *  This class implements a React component.
 */
const Properties = ({sensor}: {sensor: Sensor}) => {
  const classes = useStyles()

  return (
    <TableContainer component={Paper} className={classes.table}>
      <Table>
        <TableBody>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Value: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{sensor.getValue().toString()}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Property2: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">Bbb </Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Property3: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">Ccc </Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Property4: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">Ddd </Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow />
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Subscribed: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Checkbox color="primary" disabled inputProps={{'aria-label': 'secondary checkbox'}} />
            </StyledTableCell>
          </StyledTableRow>
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default Properties
