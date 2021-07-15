// http://localhost:3000/subscriptions/subscriptionMultipleView

import React, {useState} from 'react'
import {
  Button,
  Container,
  Grid,
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
import useSensorStore from '../../hooks/UseSensorStore'
import SubscriptionSettings from './SubscriptionSettings'
import NotificationLevel from '../../material/NotificationLevel'

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
  container: {
    marginTop: '2%',
  },
})

/**
 * Displays the webpage of the multiple subscription
 * This class implements a React component.
 */
const SubscriptionMultipleView = () => {
  const classes = useStyles()
  const sensorStore = useSensorStore()

  const [directNotification, setDirectNotification] = useState(true)
  const [notificationLevel, setNotificationLevel] = useState(new NotificationLevel(666))

  return (
    <Container maxWidth="lg" className={classes.container}>
      <Grid container spacing={3}>
        <Grid item xs={5}>
          <Typography variant="h5" gutterBottom>
            Existing subscriptions will be replaced
          </Typography>
          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <StyledTableCell>
                    <Typography variant="h5">
                      <ArrowDropDownIcon /> Sensor
                    </Typography>
                  </StyledTableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {sensorStore?.sensors.map(sensor => (
                  <StyledTableRow hover key={sensor.name.name}>
                    <StyledTableCell component="th" scope="row">
                      <Typography variant="h5">{sensor.name.name}</Typography>
                    </StyledTableCell>
                  </StyledTableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
        <Grid item xs={7}>
          <SubscriptionSettings
            directNotification={directNotification}
            setDirectNotification={setDirectNotification}
            notificationLevel={notificationLevel}
            setNotificationLevel={setNotificationLevel}
          />
          <Button variant="outlined">Subscribe</Button>
        </Grid>
      </Grid>
    </Container>
  )
}

export default SubscriptionMultipleView
