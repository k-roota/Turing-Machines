package one_tape_interpreter

import tm_general.OneTapeInstr

class InstrMap(instructions_list: Vector[OneTapeInstr]) extends Map[(String, Int), Vector[OneTapeInstr]] {
  val innerMap: Map[(String, Int), Vector[OneTapeInstr]] = instructions_list.groupBy(_.key)

  override def removed(key: (String, Int)): Map[(String, Int), Vector[OneTapeInstr]] = innerMap.removed(key)

  override def updated[V1 >: Vector[OneTapeInstr]](key: (String, Int), value: V1): Map[(String, Int), V1] = innerMap.updated(key, value)

  override def get(key: (String, Int)): Option[Vector[OneTapeInstr]] = innerMap.get(key)

  override def iterator: Iterator[((String, Int), Vector[OneTapeInstr])] = innerMap.iterator
}
