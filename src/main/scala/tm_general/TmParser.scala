package tm_general

import scala.io.Source

abstract class TmParser[A <: TmInstr](val source: Source) extends Iterator[A] {
  protected val lines: Iterator[String] = source.getLines().filter(!_.isBlank)

  override def hasNext: Boolean = {
    lines.hasNext
  }

  override def next(): A = lineToInstr(lines.next())

  def lineToInstr(line: String): A
}
