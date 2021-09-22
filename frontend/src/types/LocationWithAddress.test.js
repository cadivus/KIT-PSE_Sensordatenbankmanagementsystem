import LocationWithAddress from './LocationWithAddress'

test('getting value', () => {
  const location1 = new LocationWithAddress(12, 34, 'Teststreet 123')
  expect(location1.x).toBe(12)
  expect(location1.y).toBe(34)
  expect(location1.address).toBe('Teststreet 123')

  const location2 = new LocationWithAddress(12.34, 34.56, 'Teststreet 567')
  expect(location2.x).toBe(12.34)
  expect(location2.y).toBe(34.56)
  expect(location2.address).toBe('Teststreet 567')
})

test('to string', () => {
  const location1 = new LocationWithAddress(12, 34, 'Teststreet 123')
  expect(location1.coordinatesToString()).toBe('12 | 34')
  expect(location1.toString()).toBe('Teststreet 123: 12 | 34')
  expect(location1.addressToString()).toBe('Teststreet 123')
  expect(location1.x).toBe(12)
  expect(location1.y).toBe(34)

  const location2 = new LocationWithAddress(12.34, 34.56, 'Teststreet 567')
  expect(location2.coordinatesToString()).toBe('12.34 | 34.56')
  expect(location2.toString()).toBe('Teststreet 567: 12.34 | 34.56')
  expect(location2.addressToString()).toBe('Teststreet 567')
  expect(location2.x).toBe(12.34)
  expect(location2.y).toBe(34.56)
})
