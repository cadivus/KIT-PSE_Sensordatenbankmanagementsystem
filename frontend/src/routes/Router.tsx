import {Route, Switch, BrowserRouter} from 'react-router-dom'
import StartpageView from '../components/startpage/StartpageView'
import SensorInformationView from '../components/sensorInformation/SensorInformationView'
import LoginView from '../components/login/LoginView'
import ReplayRouter from './ReplayRouter'
import SubscribeRouter from './SubscribeRouter'

/**
 * This class routes requests from the users web browser.
 */
const Router = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/" exact>
        <StartpageView />
      </Route>
      <Route path="/sensorInformation" exact>
        <SensorInformationView />
      </Route>
      <Route path="/login" exact>
        <LoginView />
      </Route>
      <Route path="/replay">
        <ReplayRouter />
      </Route>
      <Route path="/subscription">
        <SubscribeRouter />
      </Route>
    </Switch>
  </BrowserRouter>
)

export default Router
