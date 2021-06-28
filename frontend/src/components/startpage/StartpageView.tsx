import React from 'react'
import SensorList from './SensorList'
import Search from "./Search";

/**
 *  Displays the start page.
 *  This class implements a React component.
 */
const StartpageView = () => {
  return (
    <>
      <div><b>Startpage</b></div>
      <p/>
      <Search />
      <SensorList />
    </>
  )
}

export default StartpageView
