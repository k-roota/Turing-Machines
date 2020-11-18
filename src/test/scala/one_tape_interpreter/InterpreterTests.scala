package one_tape_interpreter

import org.scalatest.FunSuite

class InterpreterTests extends FunSuite {
  def runInterpreter(path: String, maxSteps: Int, input: String): Boolean = {
    val interpreter = OneTapeInterpreter.fromFile(path, maxSteps)
    interpreter.interpret(input)
  }

  test("OneTapeInterpreter palindrome True 100") {
    assertResult(true)(runInterpreter("sample_tms/palindrome.tm", 100, "11222211"))
  }

  test("OneTapeInterpreter palindrome False 100") {
    assertResult(false)(runInterpreter("sample_tms/palindrome.tm", 100, "122122"))
  }

  test("OneTapeInterpreter palindrome False 5") {
    assertResult(false)(runInterpreter("sample_tms/palindrome.tm", 5, "11222211"))
  }

  test("OneTapeInterpreter square empty 100") {
    assertResult(true)(runInterpreter("sample_tms/square.tm", 100, ""))
  }

  test("OneTapeInterpreter square one 100") {
    assertResult(false)(runInterpreter("sample_tms/square.tm", 100, "1"))
  }

  test("OneTapeInterpreter square two 100") {
    assertResult(true)(runInterpreter("sample_tms/square.tm", 100, "11"))
  }

  test("OneTapeInterpreter square three 100") {
    assertResult(false)(runInterpreter("sample_tms/square.tm", 100, "121"))
  }

  test("OneTapeInterpreter square four 100") {
    assertResult(true)(runInterpreter("sample_tms/square.tm", 100, "1212"))
  }

//  test("OneTapeInterpreter square long 10000") {
//    assertResult(true)(runInterpreter("sample_tms/square.tm", 10000, "11122212121112221212"))
//  }
}
