import {Route, Switch, BrowserRouter} from 'react-router-dom'
import StartpageView from '../components/startpage/StartpageView'
import SensorInformationView from '../components/sensorInformation/SensorInformationView'
import LoginView from '../components/login/LoginView'
import ReplayRouter from './ReplayRouter'
import SubscribeRouter from './SubscribeRouter'
import AppLayout from '../components/layout/AppLayout'

/**
 * This class routes requests from the users web browser.
 */
const Router = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/login" exact>
        <LoginView/>
      </Route>
      <AppLayout>
        <Route path="/" exact>
          <StartpageView/>
        </Route>
        <Route path="/sensorInformation" exact>
          <SensorInformationView/>
        </Route>
        <Route path="/replay">
          <ReplayRouter/>
        </Route>
        <Route path="/subscriptions">
          <SubscribeRouter/>
        </Route>
      </AppLayout>
    </Switch>
  </BrowserRouter>
)

export default Router
