const thingMatches = (thing, thingJson) => {
  const {name, id, description, location} = thing
  const {name: jsonName, id: jsonId, description: jsonDescription, location: jsonLocation} = thingJson

  if (name.toString() !== jsonName) return "name doesn't match"
  if (id.toString() !== jsonId) return "id doesn't match"
  if (description !== jsonDescription) return "description doesn't match"

  return true
}

export const thingCollectionMatches = (thingCollection, thingJsonCollection) => {
  const jsonMap = new Map()
  for (let json of thingJsonCollection) {
    jsonMap.set(json.id, json)
  }
  if (jsonMap.size !== thingJsonCollection.length) return "there is a double id"

  for (let thing of thingCollection) {
    const json = jsonMap.get(thing.id.toString())
    if (!json) return "lists don't contain the same things"
    const matchResult = thingMatches(thing, json)
    if (matchResult !== true) return matchResult

    jsonMap.delete(json.id)
  }

  return true
}

export default thingMatches
