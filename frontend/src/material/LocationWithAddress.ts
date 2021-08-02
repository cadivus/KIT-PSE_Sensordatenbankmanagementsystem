import Location from './Location'

class LocationWithAddress extends Location {
  readonly address: string

  constructor(x: number, y: number, address: string) {
    super(x, y)
    this.address = address
  }

  addressToString = (): string => {
    const {address} = this
    return address
  }
}

export default LocationWithAddress
