import React, {FC, useState} from 'react'
import {Button, Container, Grid, Typography} from '@material-ui/core'
import {makeStyles} from '@material-ui/core/styles'
import {useHistory} from 'react-router-dom'
import {FormattedMessage} from 'react-intl'
import ThingList from './ThingList'
import Search from './Search'
import Thing from '../../material/Thing'

const useStyles = makeStyles({
  container: {
    marginTop: '15px',
  },
  buttons: {
    paddingTop: '5px',
  },
})

/**
 *  Displays the start page.
 *  This class implements a React component.
 */
const StartpageView: FC = () => {
  const history = useHistory()
  const classes = useStyles()

  const [selectedThings] = useState(new Set<Thing>())
  const [searchExpression, setSearchExpression] = useState(new RegExp('^.*$', 'i'))

  const subscribeClicked = () => {
    history.push({
      pathname: '/subscriptions/subscriptionCreate',
      // eslint-disable-next-line object-shorthand
      state: {selectedThings: selectedThings},
    })
  }

  const onReplayClick = () => {
    history.push({
      pathname: '/replay',
      // eslint-disable-next-line object-shorthand
      state: {selectedThings: selectedThings},
    })
  }

  return (
    <div>
      <Container maxWidth="lg" className={classes.container}>
        <Typography data-testid="startpage-text" variant="h3" align="center" gutterBottom>
          <FormattedMessage id="startpage.startpage" />
        </Typography>
        <Grid container spacing={3}>
          <Grid item xs={8}>
            <Search setSearchExpression={setSearchExpression} />
          </Grid>
          <Grid item xs={2}>
            <Container className={classes.buttons}>
              <Button data-testid="subscribe-button" variant="outlined" onClick={() => subscribeClicked()}>
                <FormattedMessage id="startpage.subscribe" />
              </Button>
            </Container>
          </Grid>
          <Grid item xs={2}>
            <Container className={classes.buttons}>
              <Button data-testid="replay-button" variant="outlined" onClick={onReplayClick}>
                <FormattedMessage id="startpage.replay" />
              </Button>
            </Container>
          </Grid>
        </Grid>
        <ThingList selectedThings={selectedThings} searchExpression={searchExpression} />
      </Container>
    </div>
  )
}

export default StartpageView
