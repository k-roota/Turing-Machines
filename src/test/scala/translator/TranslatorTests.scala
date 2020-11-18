package translator

import one_tape_interpreter.OneTapeInterpreter
import org.scalatest.FunSuite

class TranslatorTests extends FunSuite {
  def runInterpreter(path: String, maxSteps: Int, input: String): Boolean = {
    val newInstructions = Translator.translate(path)
//    newInstructions.foreach(println(_))
    val interpreter = new OneTapeInterpreter(newInstructions, maxSteps)
    interpreter.interpret(input)
  }

  test("Translator palindrome True 10000") {
    assertResult(true)(runInterpreter("sample_tms/two_tape.tm", 10000, "11222211"))
  }

  test("Translator palindrome False 10000") {
    assertResult(false)(runInterpreter("sample_tms/two_tape.tm", 10000, "122122"))
  }

  test("Translator palindrome False 25") {
    assertResult(false)(runInterpreter("sample_tms/two_tape.tm", 25, "11222211"))
  }

  test("Translator square empty 10000") {
    assertResult(true)(runInterpreter("sample_tms/two_square.tm", 10000, ""))
  }

  test("Translator square one 10000") {
    assertResult(false)(runInterpreter("sample_tms/two_square.tm", 10000, "1"))
  }

  test("Translator square two 10000") {
    assertResult(true)(runInterpreter("sample_tms/two_square.tm", 10000, "11"))
  }

  test("Translator square three 10000") {
    assertResult(false)(runInterpreter("sample_tms/two_square.tm", 10000, "121"))
  }

  test("Translator square four 10000") {
    assertResult(true)(runInterpreter("sample_tms/two_square.tm", 10000, "1212"))
  }

//  test("Translator square long 100000") {
//    assertResult(true)(runInterpreter("sample_tms/two_square.tm", 100000, "11122212121112221212"))
//  }
}
