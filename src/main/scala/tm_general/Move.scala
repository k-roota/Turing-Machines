package tm_general

object Move extends Enumeration {
  type Move = Value
  val L, R, S = Value

  def fromString(s: String): Move = {
    s match {
      case "L" => L
      case "R" => R
      case "S" => S
      case _ => throw new IllegalArgumentException("The move letter is incorrect")
    }
  }
}
