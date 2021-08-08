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
import useThingStore from '../../hooks/UseThingStore'
import Thing from '../../material/Thing'

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
    thingCell: {
      width: '83%',
    },
    container: {
      marginTop: '10px',
    },
  }),
)

/**
 *  Displays a list of things.
 *  This class implements a React component.
 */
const DatastreamList = ({selectedThings}: {selectedThings: Set<Thing>}) => {
  const history = useHistory()
  const classes = useStyles()
  const thingStore = useThingStore()

  const [thingList, setThingList] = useState(new Array<Thing>())
  const [lastUpdate, setLastUpdate] = useState(0)

  useEffect(() => {
    if (thingStore) {
      const newList = JSON.stringify(thingList) !== JSON.stringify(thingStore.things)

      if (newList) setThingList(thingStore.things)
      if (lastUpdate !== thingStore.lastUpdate) setLastUpdate(thingStore.lastUpdate)
    }
  }, [thingStore, lastUpdate, thingList])

  useEffect(() => {
    const interval = setInterval(() => {
      if (thingStore) {
        if (lastUpdate !== thingStore.lastUpdate) setLastUpdate(thingStore.lastUpdate)
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
            <StyledTableCell className={classes.thingCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon /> Datastreams
              </Typography>
            </StyledTableCell>
            <StyledTableCell />
            <StyledTableCell />
          </TableRow>
        </TableHead>
        <TableBody>
          {thingList.length < 1 && (
            <TableRow>
              <TableCell align="center">
                <CircularProgress className={classes.root} />
              </TableCell>
              <TableCell />
              <TableCell />
            </TableRow>
          )}
          {thingStore?.things.map(thing => {
            return (
              <StyledTableRow hover key={thing.name.name}>
                <StyledTableCell component="th" scope="row">
                  <Typography variant="h5">{thing.name.name}</Typography>
                </StyledTableCell>
                <StyledTableCell>
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={() => history.push(`/thingInformation/${thing.id.toString()}`)}
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
