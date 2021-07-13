import React from 'react'
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Theme,
  Typography,
  withStyles,
} from '@material-ui/core'
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import {createStyles, makeStyles} from '@material-ui/core/styles'
import useSensorStore from '../../hooks/UseSensorStore'

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
    width: '70%',
  },
})

/**
 *  Displays the sensors of a replay.
 *  This class implements a React component.
 */
const ReplaySensorList = () => {
  const classes = useStyles()
  const sensorStore = useSensorStore()

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <StyledTableCell className={classes.sensorCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Sensor
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Value
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {sensorStore?.sensors.map(sensor => (
            <StyledTableRow hover key={sensor.name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="body1">{sensor.name.name}</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="body1">XXXX</Typography>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default ReplaySensorList
