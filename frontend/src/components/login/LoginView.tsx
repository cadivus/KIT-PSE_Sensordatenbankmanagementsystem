// http://localhost:3000/login

import React, {useState} from 'react'
import {useHistory} from 'react-router-dom'
import LoginStep1 from './LoginStep1'
import LoginStep2 from './LoginStep2'
import useUserStore from '../../hooks/UseUserStore'
import EMail from '../../material/EMail'
import LoginCode from '../../material/LoginCode'

/**
 *  Displays the login page.
 *  This class implements a React component.
 */
const LoginView = () => {
  const history = useHistory()
  const userStore = useUserStore()

  const [secondStep, setSecondStep] = useState(false)
  const [mailString, setMailString] = useState('')
  const [codeStringUser, setCodeStringUser] = useState('')
  let codeStringAdmin: LoginCode | undefined

  const setMail = (adr: string) => {
    setMailString(adr)
    setSecondStep(true)
    codeStringAdmin = userStore?.requestStep1(new EMail(mailString))
    console.log('hier')
  }

  const requestUser = (code: string) => {
    setCodeStringUser(code)
    if (userStore?.requestUser(new EMail(mailString), new LoginCode(codeStringUser))) {
      history.push('/')
    }
  }

  return <>{!secondStep ? <LoginStep1 setMailAddress={setMail} /> : <LoginStep2 setAuthCode={requestUser} />}</>
}

export default LoginView
