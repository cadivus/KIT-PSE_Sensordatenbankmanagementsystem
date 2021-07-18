import {Route, Switch, useParams, useRouteMatch} from 'react-router-dom'
import SubscriptionChangeView from '../components/subscribe/SubscriptionChangeView'
import SubscriptionMultipleView from '../components/subscribe/SubscriptionMultipleView'
import SubscriptionSingleView from '../components/subscribe/SubscriptionSingleView'
import SubscriptionListView from '../components/subscribe/SubscriptionListView'
import useSubscriptionStore from '../hooks/UseSubscriptionStore'
import Id from '../material/Id'
import SubscriptionEditorView from '../components/subscribe/SubscriptionEditorView'

const PickChangeView = () => {
  const {subscriptionId} = useParams<{subscriptionId: string}>()
  const subscriptionStore = useSubscriptionStore()
  const subscription = subscriptionStore?.getSubscription(new Id(subscriptionId))
  const sensorsCount = subscription?.sensors.length || 0

  if (sensorsCount > 1) {
    return <SubscriptionMultipleView />
  }
  return <SubscriptionChangeView />
}

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
