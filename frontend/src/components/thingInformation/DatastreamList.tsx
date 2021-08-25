import React, {useEffect, useState} from 'react'
import {useHistory} from 'react-router-dom'
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
import {FormattedMessage} from 'react-intl'
import Thing from '../../material/Thing'
import Datastream from '../../material/Datastream'

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
const DatastreamList = ({thing}: {thing: Thing}) => {
  const history = useHistory()
  const classes = useStyles()

  const [datastreamList, setDatastreamList] = useState<Array<Datastream>>(new Array<Datastream>())
  useEffect(() => {
    thing.getDatastreams().then(list => {
      setDatastreamList(list)
    })
  })

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table}>
        <TableHead>
          <TableRow>
            <StyledTableCell className={classes.thingCell}>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="infopage.stream" />
              </Typography>
            </StyledTableCell>
            <StyledTableCell>
              <Typography variant="h5">
                <ArrowDropDownIcon />
                <FormattedMessage id="infopage.unit" />
              </Typography>
            </StyledTableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {datastreamList.map(datastream => (
            <StyledTableRow hover key={datastream.datastreamId.toString()}>
              <StyledTableCell component="th" scope="row">
                <Typography variant="h5">{datastream.name.toString()}</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Typography>{datastream.unit.toString()}</Typography>
              </StyledTableCell>
              <StyledTableCell>
                <Button
                  variant="outlined"
                  color="primary"
                  onClick={() =>
                    history.push(`/thingInformation/${thing.id.toString()}/${datastream.datastreamId.toString()}`)
                  }
                >
                  View
                </Button>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default DatastreamList
