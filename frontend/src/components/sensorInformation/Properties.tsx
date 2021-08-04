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
import Sensor from '../../material/Sensor'
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
          <Typography variant="h6">{location.addressToString()}</Typography>
          <br />
          <Typography variant="h6">{location.coordinatesToString()}</Typography>
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
              <Typography variant="h6">
                <FormattedMessage id="infopage.value" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{sensor.getValue().toString()}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <StyledTableRow>
            <StyledTableCell component="th" scope="row">
              <Typography variant="h6">Description: </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h6">{sensor.description}</Typography>
            </StyledTableCell>
          </StyledTableRow>
          <LocationRow location={sensor.location} />
          {sensor.properties.map(property => (
            <StyledTableRow key={`${sensor.id.toString()}.${property.key}`}>
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
