package tm_general

import Move._

case class OneTapeInstr(currState: String, currLetter: Int, newState: String, newLetter: Int, move: Move) extends TmInstr {
  override type KeyType = (String, Int)

  override def key: (String, Int) = (currState, currLetter)

  override def toString: String = s"$currState $currLetter $newState $newLetter $move"
}

object OneTapeInstr {
  def fromString(s: String): OneTapeInstr = {
    val words = s.split(' ').iterator
    val currState = words.next()
    val currLetter = words.next().toInt
    val newState = words.next()
    val newLetter = words.next().toInt
    val move = Move.fromString(words.next())
    OneTapeInstr(currState, currLetter, newState, newLetter, move)
  }
}