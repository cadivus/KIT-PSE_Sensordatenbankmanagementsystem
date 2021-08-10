import React, {useEffect, useState} from 'react'
import {FormattedMessage} from 'react-intl'
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
import Datastream from '../../material/Datastream'
import DatastreamRow from '../../material/DatastreamRow'

// eslint-disable-next-line @typescript-eslint/no-var-requires
const dateFormat = require('dateformat')

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

const Date = ({date}: {date: Date}) => {
  let formatted = date.toString()
  try {
    formatted = dateFormat(date, 'yyyy-mm-dd HH:MM:ss')
  } catch (e) {
    // nothing yet
  }

  return <Typography variant="h5">{formatted}</Typography>
}

/**
 *  Displays the data of a selected thing.
 *  This class implements a React component.
 */
const Data = ({datastream}: {datastream: Datastream}) => {
  const classes = useStyles()

  const [dataList, setDataList] = useState<Array<DatastreamRow>>(new Array<DatastreamRow>())
  useEffect(() => {
    datastream.getAllValues(25).then(list => {
      setDataList(list)
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
          {dataList.map(dataRow => (
            <StyledTableRow hover key={dataRow.date.toString()}>
              <StyledTableCell component="th" scope="row">
                <Date date={dataRow.date} />
              </StyledTableCell>
              <StyledTableCell>
                <Typography variant="h5">{dataRow.value.toString()}</Typography>
              </StyledTableCell>
            </StyledTableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  )
}

export default Data
