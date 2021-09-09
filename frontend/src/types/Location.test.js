import Location from './Location'

test('getting value', () => {
  const location1 = new Location(12, 34)
  expect(location1.x).toBe(12)
  expect(location1.y).toBe(34)

  const location2 = new Location(12.34, 34.56)
  expect(location2.x).toBe(12.34)
  expect(location2.y).toBe(34.56)
})

test('to string', () => {
  const location1 = new Location(12, 34)
  expect(location1.coordinatesToString()).toBe('12 | 34')
  expect(location1.toString()).toBe('12  34')
  expect(location1.x).toBe(12)
  expect(location1.y).toBe(34)

  const location2 = new Location(12.34, 34.56)
  expect(location2.coordinatesToString()).toBe('12.34 | 34.56')
  expect(location2.toString()).toBe('12.34  34.56')
  expect(location2.x).toBe(12.34)
  expect(location2.y).toBe(34.56)
})
