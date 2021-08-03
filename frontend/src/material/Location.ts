class Location {
  readonly x: number

  readonly y: number

  constructor(x: number, y: number) {
    this.x = x
    this.y = y
  }

  toString = (): string => {
    const {x, y} = this
    return `${x}  ${y}`
  }

  coordinatesToString = (): string => {
    const {x, y} = this
    return `${x} | ${y}`
  }
}

export default Location
