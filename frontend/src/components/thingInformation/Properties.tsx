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
import {FormattedMessage} from 'react-intl'
import Thing from '../../material/Thing'
import Location from '../../material/Location'
import LocationWithAddress from '../../material/LocationWithAddress'

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

const LocationRow = ({location}: {location: Location}) => {
  if (location instanceof LocationWithAddress) {
    return (
      <StyledTableRow>
        <StyledTableCell component="th" scope="row">
          <Typography variant="h6">Location: </Typography>
        </StyledTableCell>
        <StyledTableCell>
          <StyledTableRow>
            <StyledTableCell>
              <Typography variant="h6">Street:</Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{location.addressToString().split(', ')[0]}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell>
              <Typography variant="h6">City:</Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{location.addressToString().split(', ')[1]}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell>
              <Typography variant="h6">Coordinates:</Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{location.coordinatesToString()}</Typography>
            </StyledTableCell>
          </StyledTableRow>
        </StyledTableCell>
      </StyledTableRow>
    )
  }
  return (
    <StyledTableRow>
      <StyledTableCell component="th" scope="row">
        <Typography variant="h6">Location: </Typography>
      </StyledTableCell>
      <StyledTableCell>
        <Typography variant="h6">{location.toString()}</Typography>
      </StyledTableCell>
    </StyledTableRow>
  )
}

/**
 *  Displays the properties of a selected thing.
 *  This class implements a React component.
 */
const Properties = ({thing}: {thing: Thing}) => {
  const classes = useStyles()

  return (
    <TableContainer component={Paper} className={classes.table}>
      <Table>
        <TableBody>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">
                <FormattedMessage id="infopage.value" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{thing.getValue().toString()}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Description: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{thing.description}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <LocationRow location={thing.location} />
          {thing.properties.map(property => (
            <StyledTableRow key={`${thing.id.toString()}.${property.key}`}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h6">{property.key}: </Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="h6">{property.value}</Typography>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default Properties
