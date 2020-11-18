package one_tape_interpreter

import java.io.IOException

import scala.io.StdIn

object Main extends App {
  if (args.length != 2) {
    println("Set path to a Turing machine and a maximum number of steps")
  } else {
    try {
      val interpreter = OneTapeInterpreter.fromFile(args(0).trim, args(1).trim.toInt)
//      println("Insert an input word")
      val input = StdIn.readLine()
      if(interpreter.interpret(input))
        println("YES")
      else
        println("NO")
    } catch {
      case e @ (_: IOException | _: IllegalArgumentException) => println(e)
    }
  }
}
