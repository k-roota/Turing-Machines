package one_tape_interpreter

import tm_general.{Move, OneTapeInstr, Defaults}

import scala.collection.mutable.ArrayBuffer

// Inner Turing machine
class OneTapeSubTm(
                      private var tape: ArrayBuffer[Int] = ArrayBuffer[Int](Defaults.blank),
                      in_state: String = "start",
                      private var pos: Int = 0
                    ) extends Cloneable {

  private var _state = in_state

  private def state_= (newState: String): Unit = {
    _state = newState
  }

  def state: String = _state

  def letter: Int = tape(pos)


  def move(instruction: OneTapeInstr): Unit = {
//    for (i <- 0 until pos)
//      print(s" ${tape(i)} ")
//    print(s"|${tape(pos)} ")
//    for (i <- pos + 1 until tape.size)
//      print(s" ${tape(i)} ")
//    println()
//
//    println(s"$state $letter")

    tape(pos) = instruction.newLetter

    instruction.move match {
      case Move.L if pos > 0 => pos -= 1
      case Move.R =>
        pos += 1
        if (pos == tape.size)
          tape.append(Defaults.blank)
      case _ =>
    }

    state = instruction.newState
  }

  override def clone(): OneTapeSubTm = new OneTapeSubTm(tape.clone(), state, pos)
}
