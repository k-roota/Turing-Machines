package translator

import java.io.IOException

object Main extends App {
  if (args.length != 1 ) {
    println("Set path to a two-tape Turing machine")
  } else {
    try {
      Translator.translate(args(0).trim).foreach(println(_))
    } catch {
      case e @ (_: IOException | _: IllegalArgumentException) => println(e)
    }
  }
}
