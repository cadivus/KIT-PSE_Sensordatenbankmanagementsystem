import {Route, Switch, useRouteMatch} from 'react-router-dom'
import ReplayView from '../components/replay/ReplayView'

/**
 * This class routes requests to replays from the users web browser.
 */
const ReplayRouter = () => {
  const {path} = useRouteMatch()

  return (
    <Switch>
      <Route component={ReplayView} path={`${path}/`} />
    </Switch>
  )
}

export default ReplayRouter
