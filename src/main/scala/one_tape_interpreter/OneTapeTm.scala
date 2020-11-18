package one_tape_interpreter

import tm_general.Defaults

import scala.collection.mutable.ArrayBuffer

// Turing machine with submachines
class OneTapeTm(instructions: InstrMap) {

  private var subTmBuffer: ArrayBuffer[OneTapeSubTm] = _

  // accept or reject
  private var _state: Option[String] = None

  private def state_= (newState: Option[String]): Unit = {
    _state = newState
  }

  def state: Option[String] = _state

  def reset(input: Seq[Int]): Unit = {
    val tape = input.to(ArrayBuffer)
    if (tape.isEmpty)
      tape.append(Defaults.blank)
    subTmBuffer = ArrayBuffer(new OneTapeSubTm(tape))
    state = None
  }

  def move(): Unit = {
    val n = subTmBuffer.size
    for (i <- 0 until n) {
      val subTm = subTmBuffer(i)
      val key = (subTm.state, subTm.letter)

      if (instructions.contains(key)) {
        val instrVec = instructions(key)
        // create new submachines for undeterministic case
        val newSubTms = for (_ <- 1 until instrVec.size)
          yield subTm.clone()
        subTmBuffer.addAll(newSubTms)

        // move machines
        val movingSubTms = newSubTms :+ subTm
        for (i <- instrVec.indices) {
          movingSubTms(i).move(instrVec(i))
          val newSubState = movingSubTms(i).state
          if (newSubState == Defaults.accState || newSubState == Defaults.rejState) {
            state = Some(newSubState)
            return
          }
        }
      } else {
        state = Some(Defaults.rejState)
        return
      }
    }
  }
}
