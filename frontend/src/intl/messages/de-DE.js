import {LOCALES} from '../constants'

export default {
  [LOCALES.GERMAN]: {
    // Appbar
    'appbar.subscription': 'Abonnements',
    'appbar.login': 'Einloggen',
    'appbar.logout': 'Ausloggen',
    // Startpage
    'startpage.startpage': 'Startseite',
    'startpage.subscribe': 'Abonieren',
    'startpage.replay': 'Replay',
    // Infopage
    'infopage.value': 'Werte:',
    'infopage.subscribe': 'Abonieren',
    'infopage.subscribed': 'Aboniert:',
    'infopage.replay': 'Wiedergabe',
    'infopage.export': 'Exportieren',
    'infopage.fromDate': 'Start Datum',
    'infopage.toDate': 'End Datum',
    'infopage.fromTime': 'Start Zeit',
    'infopage.toTime': 'End Zeit',
    'infopage.time': 'Zeit',
    'infopage.data': 'Daten',
    'infopage.unit': 'Einheit',
    'infopage.stream': 'Datenstream',
    // Replaypage
    'replaypage.start': 'Start:',
    'replaypage.stop': 'Stop:',
    'replaypage.speed': 'Geschwindigkeit:',
    'replaypage.play': 'Abspielen',
    'replaypage.request': 'Anfrage',
    'replaypage.help': 'Hilfe?',
    'replaypage.helpTextHedding': 'Brauchst du Hilfe?',
    'replaypage.helpText1':
      'Über die Replayseite können die Einstellungen zum Replay getroffen werden. Nachdem Sie auf Play gedrückt haben, wird Ihnen ein ' +
      'Link zu dem eigentlichen Replay angezeigt. Sobald Sie diesen erstmalig öffnen fängt das Replay an zu laufen.',
    'replaypage.helpText2':
      'Sollten Sie das Replay ohne die Webseite starten wollen, können Sie dies auch tun. Dazu müssen sie einen POST Http request ' +
      'an .../api/backend/observation/newSSE machen. Dabei ist der Content-Type ein application/json. Als JSON ' +
      'werden nun die Einstellungen manuell übergeben. Die JSON Datei hat das Format:',
    'replaypage.helpText3':
      '{"start":"TT/MM/JJJJ HH:MN:SS", "end":"TT/MM/JJJJ HH:MN:SS", "speed":NUM, "sensors":["thingID"]}',
    'replaypage.helpText4': 'als Beispiel mit den Daten:',
    'replaypage.helpText5': 'Start: 21/09/2021 12:00:00',
    'replaypage.helpText6': 'End: 23/09/2021 12:00:00',
    'replaypage.helpText7': 'Speed: 100',
    'replaypage.helpText8': 'Thing: saqn:t:43ae704',
    'replaypage.helpText9':
      '{"start":"21/09/2021 12:00:00", "end":"23/09/2021 12:00:00", "speed":100, "sensors":["saqn:t:43ae704"]}',
    'replaypage.thing': 'Thing',
    'replaypage.value': 'Werte',
    // Subsciptionpage
    'subscriptionpage.notificationMessage': 'Direktbenachrichtigung im Fehlerfall',
    'subscriptionpage.protocolMessage1': 'Protokoll alle',
    'subscriptionpage.protocolMessage2': 'Tage',
    'subscriptionpage.thing': 'Thing',
    'subscriptionpage.logLevel': 'Protokoll level',
    'subscriptionpage.notificationError': 'Benachrichtigung im Fehlerfall',
    'subscriptionpage.unsubscribe': 'Deabbonieren',
    'subscriptionpage.changeButton': 'ändern',
    'subscriptionpage.deleteButton': 'löschen',
    'subscriptionpage.disagreeButton': 'nicht akzeptieren',
    'subscriptionpage.agreeButton': 'akzeptieren',
    'subscriptionpage.logMessage1': 'Alle ',
    'subscriptionpage.logMessage2': ' Tage',
    'subscriptionpage.unsubscribeMessage': 'Möchtest du wirklich das Abbonoment deabbonieren?',
    // Login
    'loginpage.signIn': 'Einloggen',
    'loginpage.signInButton': 'Einloggen',
    'loginpage.emailaddress': 'Email Addresse',
    'loginpage.password': 'Passwort',
  },
}
