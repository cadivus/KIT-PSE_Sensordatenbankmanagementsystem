import React, {FC, useState} from 'react'
import {
  Button,
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
import {createStyles, makeStyles} from '@material-ui/core/styles'
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import Checkbox from '@material-ui/core/Checkbox'
import {useHistory} from 'react-router-dom'
import useSensorStore from '../../hooks/UseSensorStore'
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
    minWidth: 800,
  },
  sensorCell: {
    width: '83%',
  },
})

/**
 *  Displays a list of sensors.
 *  This class implements a React component.
 */
const SensorList = ({selectedSensors}: {selectedSensors: Set<Sensor>}) => {
  const history = useHistory()
  const classes = useStyles()
  const sensorStore = useSensorStore()

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <StyledTableCell className={classes.sensorCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Sensor
              </Typography>
            </StyledTableCell>
            <StyledTableCell />
            <StyledTableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {sensorStore?.sensors.map(sensor => {
            const [checked, setChecked] = useState(false)

            const checkChanged = (chk: boolean) => {
              setChecked(chk)
              if (chk) {
                selectedSensors.add(sensor)
              } else {
                selectedSensors.delete(sensor)
              }
            }

            return (
              <StyledTableRow hover key={sensor.name.name}>
                <StyledTableCell component="th" scope="row">
                  <Typography variant="h5">{sensor.name.name}</Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Checkbox
                    checked={checked}
                    onChange={e => checkChanged(e.target.checked)}
                    color="primary"
                    inputProps={{'aria-label': 'secondary checkbox'}}
                  />
                </StyledTableCell>
                <StyledTableCell>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => history.push(`/sensorInformation/${sensor.id.toString()}`)}
                  >
                    Info
                  </Button>
                </StyledTableCell>
              </StyledTableRow>
            )
          })}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default SensorList
