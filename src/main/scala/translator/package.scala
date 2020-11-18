package object translator {
  implicit def bool2int(b: Boolean): Int = if (b) 1 else 0
}
