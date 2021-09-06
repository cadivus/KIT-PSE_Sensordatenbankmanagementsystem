export const datastreamRowValuesMatches = (datastreamRow, datastreamRowJson) => {
  const {value: sensorValue, date} = datastreamRow
  const {value, unit} = sensorValue

  let dateString = datastreamRowJson.RESULTTIME
  if (dateString === '') dateString = datastreamRowJson.PHENOMENONEND
  const jsonDate = new Date(dateString)
  expect(date.getTime()).toBe(jsonDate.getTime())

  const jsonValue = `${datastreamRowJson.RESULTNUMBER}`
  expect(value).toBe(jsonValue)
}

export const datastreamRowListValuesMatches = (datastreamRowList, datastreamRowJsonList) => {
  let i = 0
  for (const datastreamRow of datastreamRowList) {
    datastreamRowValuesMatches(datastreamRow, datastreamRowJsonList[i])
    i += 1
  }

  expect(datastreamRowJsonList.length).toBe(datastreamRowList.length)
}

export default datastreamRowValuesMatches
