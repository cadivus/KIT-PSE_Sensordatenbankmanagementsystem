import React, {useState} from 'react'
import Avatar from '@material-ui/core/Avatar'
import Button from '@material-ui/core/Button'
import CssBaseline from '@material-ui/core/CssBaseline'
import TextField from '@material-ui/core/TextField'
import LockOutlinedIcon from '@material-ui/icons/LockOutlined'
import Typography from '@material-ui/core/Typography'
import {makeStyles} from '@material-ui/core/styles'
import Container from '@material-ui/core/Container'
import {FormattedMessage} from 'react-intl'
import useUserStore from "../../hooks/UseUserStore";

const useStyles = makeStyles(theme => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}))

/**
 *  Displays the login page.
 *  This class implements a React component.
 */
const LoginStep2 = ({setAuthCode}) => {
  const [codeString, setCodeString] = useState('')
  const userStore = useUserStore()
  const classes = useStyles()

  function rightCode() {
    if (!userStore.confirmCode(codeString)) {
      userStore.user.logout()
    }
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          <FormattedMessage id="loginpage.signIn" />
        </Typography>
        <form className={classes.form} onSubmit={() => setAuthCode(codeString)} noValidate>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label={<FormattedMessage id="loginpage.password" />}
            type="password"
            id="password"
            autoComplete="current-password"
            onInput={e => setCodeString(e.target.value)}
          />
          <Button
            type="submit"
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
            onClick={rightCode}
          >
            <FormattedMessage id="loginpage.signInButton" />
          </Button>
        </form>
      </div>
    </Container>
  )
}

export default LoginStep2
