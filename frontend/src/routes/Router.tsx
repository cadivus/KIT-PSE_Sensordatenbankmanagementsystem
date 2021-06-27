import {Route, Switch} from 'react-router-dom'
import StartpageView from '../components/startpage/StartpageView'

/**
 * This class routes requests from the users web browser.
 */
const Router = () => {
    <Switch>
        <Route path="/" exact>
            <StartpageView />
        </Route>
    </Switch>
}

export default Router
