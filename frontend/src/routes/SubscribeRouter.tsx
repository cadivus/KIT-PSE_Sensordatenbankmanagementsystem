import {Route, Switch, useRouteMatch} from 'react-router-dom'
import SubscriptionChangeView from '../components/subscribe/SubscriptionChangeView'
import SubscriptionMultipleView from '../components/subscribe/SubscriptionMultipleView'
import SubscriptionSingleView from '../components/subscribe/SubscriptionSingleView'

/**
 * This class routes requests to subscriptions from the users web browser.
 */
const SubscribeRouter = () => {
  const {path} = useRouteMatch()

  return (
    <Switch>
      <Route path={`${path}/subscriptionChangeView`} exact>
        <SubscriptionChangeView />
      </Route>
      <Route path={`${path}/subscriptionMultipleView`} exact>
        <SubscriptionMultipleView />
      </Route>
      <Route path={`${path}/subscriptionSingleView`} exact>
        <SubscriptionSingleView />
      </Route>
    </Switch>
  )
}

export default SubscribeRouter
