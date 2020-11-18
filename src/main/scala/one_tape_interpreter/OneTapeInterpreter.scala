package one_tape_interpreter

import tm_general.{Defaults, OneTapeInstr, TmParser}

import scala.io.Source
import scala.util.Using

class OneTapeInterpreter(val instructions: Vector[OneTapeInstr], val maxSteps: Int) {

  private val tm = new OneTapeTm(new InstrMap(instructions))

  def interpret(input: String): Boolean = {
    tm.reset(input.map(_.toString.toInt))
    for (_ <- 1 to maxSteps) {
      tm.move()
      tm.state match {
        case Some(Defaults.accState) => return true
        case Some(Defaults.rejState) => return false
        case _ =>
      }
    }
    false
  }
}

object OneTapeInterpreter {
  def fromFile(path: String, maxSteps: Int): OneTapeInterpreter = {
    Using.resource(Source.fromFile(path)) { file =>
      val parser = new TmParser[OneTapeInstr](file) {
        override def lineToInstr(line: String): OneTapeInstr = OneTapeInstr.fromString(line)
      }
      new OneTapeInterpreter(parser.toVector, maxSteps)
    }
  }
}