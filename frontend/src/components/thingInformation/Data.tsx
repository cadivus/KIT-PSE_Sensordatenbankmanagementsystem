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
import {createStyles, makeStyles} from '@material-ui/core/styles'
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown'
import {FormattedMessage} from 'react-intl'
import useThingStore from '../../hooks/UseThingStore'

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
  thingCell: {
    width: '70%',
  },
})

/**
 *  Displays the data of a selected thing.
 *  This class implements a React component.
 */
const Data = () => {
  const classes = useStyles()
  const thingStore = useThingStore()

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <StyledTableCell className={classes.thingCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="infopage.time" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="infopage.data" />
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {thingStore?.things.map(thing => (
            <StyledTableRow hover key={thing.name.name}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h5">YYYY-MM-TT hh:mm:ss</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography>00xx</Typography>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default Data
