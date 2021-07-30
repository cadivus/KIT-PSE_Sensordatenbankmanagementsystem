import React, {useState, useEffect} from 'react'
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
  container: {
    marginTop: '10px',
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

  const [sensorList, setSensorList] = useState(new Array<Sensor>())
  const [lastUpdate, setLastUpdate] = useState(0)

  const [selectedSensorsState, setSelectedSensorsState] = useState(new Set<Sensor>())

  useEffect(() => {
    if (sensorStore) {
      const newList = JSON.stringify(sensorList) !== JSON.stringify(sensorStore.sensors)

      if (newList) setSensorList(sensorStore.sensors)
      if (lastUpdate !== sensorStore.lastUpdate) setLastUpdate(sensorStore.lastUpdate)
    }
  }, [sensorStore, lastUpdate, sensorList])

  useEffect(() => {
    const interval = setInterval(() => {
      if (sensorStore) {
        if (lastUpdate !== sensorStore.lastUpdate) setLastUpdate(sensorStore.lastUpdate)
      }
    }, 1000)

    return () => {
      clearInterval(interval)
    }
  })

  return (
    <TableContainer component={Paper} className={classes.container}>
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
            const checkChanged = (chk: boolean) => {
              if (chk && !selectedSensorsState.has(sensor)) {
                selectedSensors.add(sensor)
                setSelectedSensorsState(new Set<Sensor>(selectedSensors))
              } else if (selectedSensorsState.has(sensor)) {
                selectedSensors.delete(sensor)
                setSelectedSensorsState(new Set<Sensor>(selectedSensors))
              }
            }

            return (
              <StyledTableRow hover key={sensor.name.name}>
                <StyledTableCell component="th" scope="row">
                  <Typography variant="h5">{sensor.name.name}</Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Checkbox
                    checked={selectedSensorsState.has(sensor)}
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
