import {LOCALES} from '../constants'

export default {
  [LOCALES.ENGLISH]: {
    // Appbar
    'appbar.subscription': 'SUBSCRIPTIONS',
    'appbar.login': 'LOGIN',
    'appbar.logout': 'LOGOUT',
    // Startpage
    'startpage.startpage': 'Startpage',
    'startpage.subscribe': 'Subscribe',
    'startpage.replay': 'Replay',
    // Infopage
    'infopage.value': 'Value:',
    'infopage.subscribe': 'Subscribe',
    'infopage.subscribed': 'Subscribed:',
    'infopage.replay': 'Replay',
    'infopage.export': 'Export',
    'infopage.fromDate': 'From date',
    'infopage.toDate': 'To date',
    'infopage.fromTime': 'From time',
    'infopage.toTime': 'To time',
    'infopage.time': 'Time',
    'infopage.data': 'Data',
    'infopage.unit': 'Unit',
    'infopage.stream': 'Datastream',
    // Replaypage
    'replaypage.start': 'Start:',
    'replaypage.stop': 'Stop:',
    'replaypage.speed': 'Speed:',
    'replaypage.play': 'Play',
    'replaypage.request': 'Request',
    'replaypage.help': 'Help?',
    'replaypage.helpTextHedding': 'Need Help?',
    'replaypage.helpText1':
      'The replay page can be used to make the settings for the replay. After pressing Play you will see a link to the actual replay. ' +
      'link to the actual replay will be displayed. As soon as you open this link for the first time, the replay will start.',
    'replaypage.helpText2':
      'If you want to start the replay without the web page, you can also do this. For this you have to make a POST Http request ' +
      'to .../api/backend/observation/newSSE. The content type is application/json. As JSON ' +
      'the settings will be passed manually. The JSON file has the format:',
    'replaypage.helpText3':
      '{"start":"TT/MM/JJJJ HH:MN:SS", "end":"TT/MM/JJJJ HH:MN:SS", "speed":NUM, "sensors":["thingID"]}',
    'replaypage.helpText4': 'as an example with the dates:',
    'replaypage.helpText5': 'Start: 21/09/2021 12:00:00',
    'replaypage.helpText6': 'End: 23/09/2021 12:00:00',
    'replaypage.helpText7': 'Speed: 100',
    'replaypage.helpText8': 'Thing: saqn:t:43ae704',
    'replaypage.helpText9':
      '{"start":"21/09/2021 12:00:00", "end":"23/09/2021 12:00:00", "speed":100, "sensors":["saqn:t:43ae704"]}',
    'replaypage.thing': 'Thing',
    'replaypage.value': 'Value',
    // Subsciptionpage
    'subscriptionpage.notificationMessage': 'Direct notification on failures',
    'subscriptionpage.protocolMessage1': 'Protocol every',
    'subscriptionpage.protocolMessage2': 'days',
    'subscriptionpage.thing': 'Thing',
    'subscriptionpage.logLevel': 'Log level',
    'subscriptionpage.notificationError': 'Notification on Error',
    'subscriptionpage.unsubscribe': 'Unsubscribe',
    'subscriptionpage.changeButton': 'CHANGE',
    'subscriptionpage.deleteButton': 'DELETE',
    'subscriptionpage.disagreeButton': 'Disagree',
    'subscriptionpage.agreeButton': 'Agree',
    'subscriptionpage.logMessage1': 'Every ',
    'subscriptionpage.logMessage2': ' days',
    'subscriptionpage.unsubscribeMessage': 'Do you really want to unsubscribe this subscription?',
    // Login
    'loginpage.signIn': 'Sign in',
    'loginpage.signInButton': 'SIGN IN',
    'loginpage.emailaddress': 'Email Address',
    'loginpage.password': 'Password',
  },
}
