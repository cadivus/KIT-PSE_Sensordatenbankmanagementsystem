import {FC} from 'react'
import {Route, Switch, BrowserRouter} from 'react-router-dom'
import StartpageView from '../components/startpage/StartpageView'
import ThingInformationView from '../components/thingInformation/ThingInformationView'
import LoginView from '../components/login/LoginView'
import ReplayRouter from './ReplayRouter'
import SubscribeRouter from './SubscribeRouter'
import AppLayout from '../components/layout/AppLayout'
import DatastreamView from '../components/datastreams/DatastreamView'

/**
 * Resets the children by creating a new div.
 *
 * @param children Children to reset
 */
const Reset: FC = ({children}) => <div key={`${new Date().getTime()}`}>{children}</div>

/**
 * This class routes requests from the users web browser.
 */
const Router = () => (
  <BrowserRouter>
    <Switch>
      <Route path="/login" exact>
        <Reset>
          <LoginView />
        </Reset>
      </Route>
      <AppLayout>
        <Route path="/" exact>
          <StartpageView />
        </Route>
        <Route path="/thingInformation/:thingId" exact>
          <ThingInformationView />
        </Route>
        <Route path="/thingInformation/:thingId/:datastreamId" exact>
          <DatastreamView />
        </Route>
        <Route path="/replay">
          <ReplayRouter />
        </Route>
        <Route path="/subscriptions">
          <SubscribeRouter />
        </Route>
      </AppLayout>
    </Switch>
  </BrowserRouter>
)

export default Router
