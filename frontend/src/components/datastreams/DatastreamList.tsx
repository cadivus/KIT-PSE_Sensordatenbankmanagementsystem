import React, {useState, useEffect} from 'react'
import {
  Button,
  CircularProgress,
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

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
      '& > * + *': {
        marginLeft: theme.spacing(2),
      },
      marginLeft: '60%',
    },
    table: {
      minWidth: 800,
    },
    sensorCell: {
      width: '83%',
    },
    container: {
      marginTop: '10px',
    },
  }),
)

/**
 *  Displays a list of sensors.
 *  This class implements a React component.
 */
const DatastreamList = ({selectedSensors}: {selectedSensors: Set<Sensor>}) => {
  const history = useHistory()
  const classes = useStyles()
  const sensorStore = useSensorStore()

  const [sensorList, setSensorList] = useState(new Array<Sensor>())
  const [lastUpdate, setLastUpdate] = useState(0)

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
                <ArrowDropDownIcon /> Datastreams
              </Typography>
            </StyledTableCell>
            <StyledTableCell />
            <StyledTableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {sensorList.length < 1 && (
            <TableRow>
              <TableCell align="center">
                <CircularProgress className={classes.root} />
              </TableCell>
              <TableCell />
              <TableCell />
            </TableRow>
          )}
          {sensorStore?.sensors.map(sensor => {
            return (
              <StyledTableRow hover key={sensor.name.name}>
                <StyledTableCell component="th" scope="row">
                  <Typography variant="h5">{sensor.name.name}</Typography>
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

export default DatastreamList
