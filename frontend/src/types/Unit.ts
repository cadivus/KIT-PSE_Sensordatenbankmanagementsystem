const TRANSLATOR: Record<string, string> = {
  'microgram per cubic meter': 'µg/m3',
  Hectopascal: 'hPa',
  HeKtopascal: 'hPa',
  'Degree Celsius': '°C',
  'degree Celsius': '°C',
  percent: '%',
  'kilometer per hour': 'km/h',
}

class Unit {
  readonly name: string

  constructor(name: string) {
    this.name = name
  }

  toString = (): string => {
    const {name} = this
    const translated = TRANSLATOR[name]

    // eslint-disable-next-line no-unneeded-ternary
    return translated ? translated : name
  }
}

export default Unit
