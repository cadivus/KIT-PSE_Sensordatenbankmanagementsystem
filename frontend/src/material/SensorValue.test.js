import SensorValue from './SensorValue'
import Unit from './Unit'

test('values with unit', () => {
  const unit1 = new Unit('test123unit')
  const sensorValue1 = new SensorValue('123', unit1)
  expect(sensorValue1.toString()).toBe('123 test123unit')
  expect(sensorValue1.valueToString()).toBe('123')

  const unit2 = new Unit('test456unit')
  const sensorValue2 = new SensorValue('567', unit2)
  expect(sensorValue2.toString()).toBe('567 test456unit')
  expect(sensorValue2.valueToString()).toBe('567')
})
