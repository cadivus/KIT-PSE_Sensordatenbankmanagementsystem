import Unit from './Unit'

test('custom values', () => {
  const unit1 = new Unit('test123')
  expect(unit1.toString()).toBe('test123')

  const unit2 = new Unit('test456')
  expect(unit2.toString()).toBe('test456')
})

test('translated values', () => {
  const translated = {
    'microgram per cubic meter': 'µg/m3',
    Hectopascal: 'hPa',
    HeKtopascal: 'hPa',
    'Degree Celsius': '°C',
    'degree Celsius': '°C',
    percent: '%',
    'kilometer per hour': 'km/h',
  }

  for (var key in translated) {
    const value = translated[key]

    const unit = new Unit(key)
    expect(unit.toString()).toBe(value)
  }
})
