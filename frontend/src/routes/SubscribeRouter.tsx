import {Route, Switch, useRouteMatch} from 'react-router-dom'
import SubscriptionListView from '../components/subscribe/SubscriptionListView'
import SubscriptionEditorView from '../components/subscribe/SubscriptionEditorView'

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
      <Route component={SubscriptionEditorView} path={`${path}/subscriptionChange/:subscriptionId`} />
      <Route path={`${path}/subscriptionChange/:subscriptionId`}>
        <SubscriptionEditorView />
      </Route>
      <Route component={SubscriptionEditorView} path={`${path}/subscriptionCreate`} />
    </Switch>
  )
}

export default SubscribeRouter
