import React, {useEffect, useState} from 'react'
import FiberManualRecordIcon from '@material-ui/icons/FiberManualRecord'
import {green, grey, red} from '@material-ui/core/colors'
import {Typography} from '@material-ui/core'
import Thing, {ThingState} from '../../types/Thing'

const ACTIVE_STATE_REFRESH = 10000

const ThingTitle = ({thing}: {thing: Thing}) => {
  const [activeState, setActiveState] = useState(ThingState.Unknown)
  useEffect(() => {
    if (thing) {
      thing.isActive().then(state => {
        setActiveState(state)
      })
    }

    const interval = setInterval(() => {
      if (!thing) return
      thing.isActive().then(state => {
        setActiveState(state)
      })
    }, ACTIVE_STATE_REFRESH)

    return () => {
      clearInterval(interval)
    }
  }, [thing])

  return (
    <Typography variant="h3" align="center" gutterBottom data-testid="thingTitle">
      {thing.name.toString()}
      {activeState === ThingState.Online ? (
        <FiberManualRecordIcon style={{color: green[500]}} fontSize="large" />
      ) : activeState === ThingState.Offline ? (
        <FiberManualRecordIcon style={{color: red[500]}} fontSize="large" />
      ) : (
        <FiberManualRecordIcon style={{color: grey[500]}} fontSize="large" /> /* Unknown state */
      )}
    </Typography>
  )
}

export default ThingTitle
