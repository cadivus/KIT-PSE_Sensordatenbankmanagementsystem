const thingMatches = (thing, thingJson) => {
  const {name, id, description, location} = thing
  const {name: jsonName, id: jsonId, description: jsonDescription, location: jsonLocation} = thingJson

  expect(name.toString()).toBe(jsonName)
  expect(id.toString()).toBe(jsonId)
  expect(description).toBe(jsonDescription)
}

export const thingCollectionMatches = (thingCollection, thingJsonCollection) => {
  const jsonMap = new Map()
  for (const json of thingJsonCollection) {
    jsonMap.set(json.id, json)
  }
  expect(thingJsonCollection.length).toBe(jsonMap.size)

  for (const thing of thingCollection) {
    const json = jsonMap.get(thing.id.toString())
    thingMatches(thing, json)

    jsonMap.delete(json.id)
  }
}

export default thingMatches
