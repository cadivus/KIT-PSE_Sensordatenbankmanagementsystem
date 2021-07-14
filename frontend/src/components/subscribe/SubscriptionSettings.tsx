import React from 'react'
import {Table, TableBody, TableCell, TableContainer, TableRow, TextField, Typography} from '@material-ui/core'
import Checkbox from '@material-ui/core/Checkbox'
import {makeStyles} from '@material-ui/core/styles'

const useStyles = makeStyles({
  Numberfield: {
    marginLeft: '20px',
    marginRight: '20px',
  },
})

/**
 * Shows the settings of a single subscribtion (sensor, direct notification, notification level).
 * This class implements a React component.
 */
const SubscriptionSettings = () => {
  const classes = useStyles()

  return (
    <div>
      <TableContainer>
        <Table>
          <TableBody>
            <TableRow>
              <TableCell component="th" scope="row">
                <Checkbox defaultChecked color="primary" inputProps={{'aria-label': 'secondary checkbox'}} />
              </TableCell>
              <TableCell>
                <Typography variant="body1">Direct notification on failures</Typography>
              </TableCell>
            </TableRow>
            <TableRow>
              <TableCell component="th" scope="row">
                <Checkbox defaultChecked color="primary" inputProps={{'aria-label': 'secondary checkbox'}} />
              </TableCell>
              <TableCell>
                <Typography variant="body1">
                  Protocol every
                  <TextField
                    id="standard-number"
                    label="Number"
                    type="number"
                    InputLabelProps={{
                      shrink: true,
                    }}
                    className={classes.Numberfield}
                  />
                  days
                </Typography>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  )
}

export default SubscriptionSettings
