/**
 * Objects of this class represent an Id
 */
class Id {
  /**
   * The login code represented as a string.
   */
  readonly id: string

  constructor(id: string) {
    this.id = id
  }

  toString(): string {
    const {id} = this
    return id
  }
}

export default Id
