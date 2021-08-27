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
import Checkbox from '@material-ui/core/Checkbox'
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
const ThingList = ({selectedThings, searchExpression}: {selectedThings: Set<Thing>; searchExpression: RegExp}) => {
  const history = useHistory()
  const classes = useStyles()
  const thingStore = useThingStore()

  const [thingList, setThingList] = useState(new Array<Thing>())
  const [lastUpdate, setLastUpdate] = useState(0)

  const [selectedThingsState, setSelectedThingsState] = useState(new Set<Thing>())

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
                <ArrowDropDownIcon /> Thing
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
            const checkChanged = (chk: boolean) => {
              if (chk && !selectedThingsState.has(thing)) {
                selectedThings.add(thing)
                setSelectedThingsState(new Set<Thing>(selectedThings))
              } else if (selectedThingsState.has(thing)) {
                selectedThings.delete(thing)
                setSelectedThingsState(new Set<Thing>(selectedThings))
              }
            }
            if (thing.name.toString().match(searchExpression)) {
              return (
                <StyledTableRow hover key={thing.name.name}>
                  <StyledTableCell component="th" scope="row">
                    <Typography variant="h5">{thing.name.name}</Typography>
                  </StyledTableCell>
                  <StyledTableCell>
                    <Checkbox
                      checked={selectedThingsState.has(thing)}
                      onChange={e => checkChanged(e.target.checked)}
                      color="primary"
                      inputProps={{'aria-label': 'secondary checkbox'}}
                    />
                  </StyledTableCell>
                  <StyledTableCell>
                    <Button
                      variant="outlined"
                      color="primary"
                      onClick={() => history.push(`/thingInformation/${thing.id.toString()}`)}
                    >
                      Datastream
                    </Button>
                  </StyledTableCell>
                </StyledTableRow>
              )
            }
            return <></>
          })}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default ThingList
