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
import {FormattedMessage} from 'react-intl'
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

const useStyles = makeStyles({
  thingCell: {
    width: '70%',
  },
})

/**
 *  Displays the things of a replay.
 *  This class implements a React component.
 */
const ReplayThingList = ({things}: {things: Set<Thing>}) => {
  const classes = useStyles()
  const thingStore = useThingStore()

  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <StyledTableCell className={classes.thingCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="replaypage.thing" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="replaypage.value" />
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {Array.from(things).map(thing => (
            <StyledTableRow hover key={thing.name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="body1">{thing.name.toString()}</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="body1">{thing.id.toString()}</Typography>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default ReplayThingList
