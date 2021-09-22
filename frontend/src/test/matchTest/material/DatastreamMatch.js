const datastreamMatches = (datastream, datastreamJson) => {
  const {name, datastreamId} = datastream
  const {name: jsonName, id: jsonId} = datastreamJson

  expect(name.toString()).toBe(jsonName)
  expect(datastreamId.toString()).toBe(jsonId)
}

export const datastreamCollectionMatches = (datastreamCollection, datastreamJsonCollection) => {
  const jsonMap = new Map()
  for (const json of datastreamJsonCollection) {
    jsonMap.set(json.id, json)
  }
  expect(datastreamJsonCollection.length).toBe(jsonMap.size)

  for (const datastream of datastreamCollection) {
    const json = jsonMap.get(datastream.datastreamId.toString())
    datastreamMatches(datastream, json)

    jsonMap.delete(json.id)
  }
}

export default datastreamMatches
