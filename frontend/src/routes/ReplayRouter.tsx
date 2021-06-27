import {Route, Switch, useRouteMatch} from 'react-router-dom'
import ReplayMultipleView from '../components/replay/ReplayMultipleView'
import ReplaySingleView from '../components/replay/ReplaySingleView'

/**
 * This class routes requests to replays from the users web browser.
 */
const ReplayRouter = () => {
  const {path} = useRouteMatch()

  return (
    <Switch>
      <Route path={`${path}/replayMultipleView`} exact>
        <ReplayMultipleView />
      </Route>
      <Route path={`${path}/replaySingleView`} exact>
        <ReplaySingleView />
      </Route>
    </Switch>
  )
}

export default ReplayRouter
