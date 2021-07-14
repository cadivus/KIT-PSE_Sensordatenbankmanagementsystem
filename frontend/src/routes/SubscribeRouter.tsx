import {Route, Switch, useRouteMatch} from 'react-router-dom'
import SubscriptionChangeView from '../components/subscribe/SubscriptionChangeView'
import SubscriptionMultipleView from '../components/subscribe/SubscriptionMultipleView'
import SubscriptionSingleView from '../components/subscribe/SubscriptionSingleView'
import SubscriptionListView from '../components/subscribe/SubscriptionListView'

/**
 * This class routes requests to subscriptions from the users web browser.
 */
const SubscribeRouter = () => {
  const {path} = useRouteMatch()

  return (
    <Switch>
      <Route path={`${path}/`} exact>
        <SubscriptionListView />
      </Route>
      <Route path={`${path}/subscriptionChangeView`}>
        <SubscriptionChangeView />
      </Route>
      <Route path={`${path}/subscriptionMultipleView`}>
        <SubscriptionMultipleView />
      </Route>
      <Route path={`${path}/subscriptionSingleView`}>
        <SubscriptionSingleView />
      </Route>
    </Switch>
  )
}

export default SubscribeRouter
