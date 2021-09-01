import ReplaySpeed from './ReplaySpeed'

test('getting value', () => {
  const replaySpeed1 = new ReplaySpeed(10)
  expect(replaySpeed1.toNumber()).toBe(10)

  const replaySpeed2 = new ReplaySpeed(10.5)
  expect(replaySpeed2.toNumber()).toBe(10.5)

  const replaySpeed3 = new ReplaySpeed(0.5)
  expect(replaySpeed3.toNumber()).toBe(0.5)
})

test('value to strong', () => {
  const replaySpeed1 = new ReplaySpeed(10)
  expect(replaySpeed1.toString()).toBe('10')

  const replaySpeed2 = new ReplaySpeed(10.5)
  expect(replaySpeed2.toString()).toBe('10.5')

  const replaySpeed3 = new ReplaySpeed(0.5)
  expect(replaySpeed3.toString()).toBe('0.5')
})