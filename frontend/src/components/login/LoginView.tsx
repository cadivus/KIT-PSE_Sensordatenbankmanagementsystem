// http://localhost:3000/login

import React, {useState} from 'react'
import LoginStep1 from './LoginStep1'
import LoginStep2 from './LoginStep2'
import useUserStore from '../../hooks/UseUserStore'
import EMail from "../../material/EMail";
import LoginCode from "../../material/LoginCode";
import {useHistory} from "react-router-dom";

/**
 *  Displays the login page.
 *  This class implements a React component.
 */
const LoginView = () => {
  const history = useHistory()
  const userStore = useUserStore()

  const [secondStep, setSecondStep] = useState(false)
  const [mailString, setMailString] = useState('')
  const [codeString, setCodeString] = useState('')

  const setMail = (adr: string) => {
    setMailString(adr)
    setSecondStep(true)
  }

  const requestUser = (code: string) => {
    setCodeString(code)
    if (userStore?.requestUser(new EMail(mailString), new LoginCode(codeString)))
    {
      history.push("/")
    }
  }

  return (
    <>
      {!secondStep ? (
        <LoginStep1 setMailAdress={setMail} />
      ) : (
        <LoginStep2 setAuthCode={requestUser} />
      )}
    </>
  )
}

export default LoginView