package translator

import tm_general.Move._
import tm_general.{Move, TmInstr}

case class TwoTapeInstr(currState: String, currLetters: Vector[Int], newState: String, newLetters: Vector[Int], moves: Vector[Move]) extends TmInstr {
  override type KeyType = (String, Vector[Int])

  override def key: (String, Vector[Int]) = (currState, currLetters)
}

object TwoTapeInstr {
  def fromString(s: String): TwoTapeInstr = {
    val words = s.split(' ').iterator
    val currState = words.next()
    val currLetters = Vector(words.next().toInt, words.next().toInt)
    val newState = words.next()
    val newLetters = Vector(words.next().toInt, words.next().toInt)
    val moves = Vector(Move.fromString(words.next()), Move.fromString(words.next()))
    TwoTapeInstr(currState, currLetters, newState, newLetters, moves)
  }
}
