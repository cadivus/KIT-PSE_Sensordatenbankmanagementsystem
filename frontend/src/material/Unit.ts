class Unit {
  readonly name: string

  constructor(name: string) {
    this.name = name
  }

  toString = (): string => {
    const {name} = this
    return name
  }

  getTranslateId = (): string => {
    const {name} = this
    const id = `units.${name.replace(' ', '_')}`
    return id
  }
}

export default Unit
