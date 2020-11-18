package translator

// substates used in one-tape machine - searching for a head, moving head, returning to the start position
object SubState extends Enumeration {
  type SubState = Value
  val Return, Search, Move = Value
}
