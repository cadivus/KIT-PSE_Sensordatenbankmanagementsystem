import React from 'react'
import {Table, TableBody, TableCell, TableContainer, TableRow, TextField, Typography} from '@material-ui/core'
import Checkbox from '@material-ui/core/Checkbox'
import {makeStyles} from '@material-ui/core/styles'
// eslint-disable-next-line import/no-unresolved
import NotificationLevel from '../../material/NotificationLevel'

const useStyles = makeStyles({
  Numberfield: {
    marginLeft: '20px',
    marginRight: '20px',
  },
})

/**
 * Shows the settings of a single subscribtion (thing, direct notification, notification level).
 * This class implements a React component.
 */
// eslint-disable-next-line max-len
const SubscriptionSettings = ({directNotification, setDirectNotification, notificationLevel, setNotificationLevel}) => {
  const classes = useStyles()

  return (
    <div>
      <TableContainer>
        <Table>
          <TableBody>
            <TableRow data-testid="directNotification-Row">
              <TableCell component="th" scope="row">
                <Checkbox
                  checked={directNotification}
                  color="primary"
                  inputProps={{'aria-label': 'secondary checkbox'}}
                  onChange={e => setDirectNotification(e.target.checked)}
                />
              </TableCell>
              <TableCell>
                <Typography variant="body1">Direct notification on failures</Typography>
              </TableCell>
            </TableRow>
            <TableRow data-testid="protocol-Row">
              <TableCell component="th" scope="row">
                <Checkbox
                  checked={notificationLevel.active}
                  color="primary"
                  inputProps={{'aria-label': 'secondary checkbox'}}
                  // eslint-disable-next-line max-len
                  onChange={e => setNotificationLevel(new NotificationLevel(notificationLevel.days, e.target.checked))}
                />
              </TableCell>
              <TableCell>
                <Typography variant="body1">
                  Protocol every
                  <TextField
                    id="standard-number"
                    label="Number"
                    type="number"
                    value={notificationLevel.days}
                    InputLabelProps={{
                      shrink: true,
                    }}
                    className={classes.Numberfield}
                    // eslint-disable-next-line max-len
                    onInput={e => setNotificationLevel(new NotificationLevel(e.target.value, notificationLevel.active))}
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
