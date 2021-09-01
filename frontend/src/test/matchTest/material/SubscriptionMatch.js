const subscriptionMatches = (subscription, subscriptionJson) => {
  const {id, thing, directNotification, owner, notificationLevel} = subscription
  const {id: jsonId, sensorId: jsonSensor, subscriberAddress: jsonSubscriberAddress, reportInterval: jsonReportInterval, toggleAlert: jsonToggleAlert} = subscriptionJson

  // expect(id.toString()).toBe(jsonId)
  expect(thing.id.toString()).toBe(jsonSensor)
  expect(owner.email.toString()).toBe(jsonSubscriberAddress)
}

export const subscriptionCollectionMatches = (subscriptionCollection, subscriptionJsonCollection) => {
  const jsonMap = new Map()
  for (let json of subscriptionJsonCollection) {
    // jsonMap.set(json.id, json)
    jsonMap.set(json.sensorId, json)
  }
  expect(subscriptionJsonCollection.length).toBe(jsonMap.size)

  for (let subscription of subscriptionCollection) {
    const json = jsonMap.get(subscription.id.toString())
    subscriptionMatches(subscription, json)

    jsonMap.delete(json.id)
  }
}

export default subscriptionCollectionMatches
