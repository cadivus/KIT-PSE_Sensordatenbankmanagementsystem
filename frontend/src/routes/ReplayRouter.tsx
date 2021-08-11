import {Route, Switch, useRouteMatch} from 'react-router-dom'
import ReplayMultipleView from '../components/replay/ReplayMultipleView'

/**
 * This class routes requests to replays from the users web browser.
 */
const ReplayRouter = () => {
  const {path} = useRouteMatch()

  return (
    <Switch>
      <Route component={ReplayMultipleView} path={`${path}/`} />
    </Switch>
  )
}

export default ReplayRouter
