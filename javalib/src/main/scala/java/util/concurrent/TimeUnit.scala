package java.util.concurrent

abstract class TimeUnit private (_index: Int,
    _name: String) extends java.io.Serializable {

  def convert(a: Long, u: TimeUnit): Long

  def toNanos(a: Long): Long
  def toMicros(a: Long): Long
  def toMillis(a: Long): Long
  def toSeconds(a: Long): Long
  def toMinutes(a: Long): Long
  def toHours(a: Long): Long
  def toDays(a: Long): Long

  // not used
  //private[concurrent] def excessNanos(a: Long, b: Long): Int

  def name(): String = _name
  def ordinal(): Int = _index

  // methods that cannot be implemented
  //def timedWait(arg1: AnyRef, arg2: Long): Unit
  //def timedJoin(arg1: Thread, arg2: Long): Unit
  //def sleep(arg1: Long): Unit

  override def toString() = name()
}

object TimeUnit {
  final val NANOSECONDS: TimeUnit = new TimeUnit(0, "NANOSECONDS") {
    def convert(a: Long, u: TimeUnit): Long = u.toNanos(a)
    def toNanos(a: Long): Long   = a
    def toMicros(a: Long): Long  = a / (C1/C0)
    def toMillis(a: Long): Long  = a / (C2/C0)
    def toSeconds(a: Long): Long = a / (C3/C0)
    def toMinutes(a: Long): Long = a / (C4/C0)
    def toHours(a: Long): Long   = a / (C5/C0)
    def toDays(a: Long): Long    = a / (C6/C0)
  }

  final val MICROSECONDS: TimeUnit = new TimeUnit(1, "MICROSECONDS") {
    def convert(a: Long, u: TimeUnit): Long = u.toMicros(a)
    def toNanos(a: Long): Long   = x(a, C1/C0, MAX/(C1/C0))
    def toMicros(a: Long): Long  = a
    def toMillis(a: Long): Long  = a / (C2/C1)
    def toSeconds(a: Long): Long = a / (C3/C1)
    def toMinutes(a: Long): Long = a / (C4/C1)
    def toHours(a: Long): Long   = a / (C5/C1)
    def toDays(a: Long): Long    = a / (C6/C1)
  }

  final val MILLISECONDS: TimeUnit = new TimeUnit(2, "MILLISECONDS") {
    def convert(a: Long, u: TimeUnit): Long = u.toMillis(a)
    def toNanos(a: Long): Long   = x(a, C2/C0, MAX/(C2/C0))
    def toMicros(a: Long): Long  = x(a, C2/C1, MAX/(C2/C1))
    def toMillis(a: Long): Long  = a
    def toSeconds(a: Long): Long = a / (C3/C2)
    def toMinutes(a: Long): Long = a / (C4/C2)
    def toHours(a: Long): Long   = a / (C5/C2)
    def toDays(a: Long): Long    = a / (C6/C2)
  }

  final val SECONDS: TimeUnit = new TimeUnit(3, "SECONDS") {
    def convert(a: Long, u: TimeUnit): Long = u.toSeconds(a)
    def toNanos(a: Long): Long   = x(a, C3/C0, MAX/(C3/C0))
    def toMicros(a: Long): Long  = x(a, C3/C1, MAX/(C3/C1))
    def toMillis(a: Long): Long  = x(a, C3/C2, MAX/(C3/C2))
    def toSeconds(a: Long): Long = a
    def toMinutes(a: Long): Long = a / (C4/C3)
    def toHours(a: Long): Long   = a / (C5/C3)
    def toDays(a: Long): Long    = a / (C6/C3)
  }

  final val MINUTES: TimeUnit = new TimeUnit(4, "MINUTES") {
    def convert(a: Long, u: TimeUnit): Long = u.toMinutes(a)
    def toNanos(a: Long): Long   = x(a, C4/C0, MAX/(C4/C0))
    def toMicros(a: Long): Long  = x(a, C4/C1, MAX/(C4/C1))
    def toMillis(a: Long): Long  = x(a, C4/C2, MAX/(C4/C2))
    def toSeconds(a: Long): Long = x(a, C4/C3, MAX/(C4/C3))
    def toMinutes(a: Long): Long = a
    def toHours(a: Long): Long   = a / (C5/C4)
    def toDays(a: Long): Long    = a / (C6/C4)
  }

  final val HOURS: TimeUnit = new TimeUnit(5, "HOURS") {
    def convert(a: Long, u: TimeUnit): Long = u.toHours(a)
    def toNanos(a: Long): Long   = x(a, C5/C0, MAX/(C5/C0))
    def toMicros(a: Long): Long  = x(a, C5/C1, MAX/(C5/C1))
    def toMillis(a: Long): Long  = x(a, C5/C2, MAX/(C5/C2))
    def toSeconds(a: Long): Long = x(a, C5/C3, MAX/(C5/C3))
    def toMinutes(a: Long): Long = x(a, C5/C4, MAX/(C5/C4))
    def toHours(a: Long): Long   = a
    def toDays(a: Long): Long    = a / (C6/C5)
  }

  final val DAYS: TimeUnit = new TimeUnit(6, "DAYS") {
    def convert(a: Long, u: TimeUnit): Long = u.toDays(a)
    def toNanos(a: Long): Long   = x(a, C6/C0, MAX/(C6/C0))
    def toMicros(a: Long): Long  = x(a, C6/C1, MAX/(C6/C1))
    def toMillis(a: Long): Long  = x(a, C6/C2, MAX/(C6/C2))
    def toSeconds(a: Long): Long = x(a, C6/C3, MAX/(C6/C3))
    def toMinutes(a: Long): Long = x(a, C6/C4, MAX/(C6/C4))
    def toHours(a: Long): Long   = x(a, C6/C5, MAX/(C6/C5))
    def toDays(a: Long): Long    = a
  }

  private[this] val _values: Array[TimeUnit] =
    Array(NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS)

  // deliberately without type ascription to make them compile-time constants
  final private[concurrent] val C0 = 1L
  final private[concurrent] val C1 = C0 * 1000L
  final private[concurrent] val C2 = C1 * 1000L
  final private[concurrent] val C3 = C2 * 1000L
  final private[concurrent] val C4 = C3 * 60L
  final private[concurrent] val C5 = C4 * 60L
  final private[concurrent] val C6 = C5 * 24L
  final private[concurrent] val MAX = Long.MaxValue

  def values(): Array[TimeUnit] = _values.clone()

  def valueOf(name: String): TimeUnit = {
    _values.find(_.name == name).getOrElse(
        throw new IllegalArgumentException("No enum const TimeUnit." + name))
  }

  private[concurrent] def x(a: Long, b: Long, max: Long): Long = {
    if (a > max) MAX
    else if (a < -max) -MAX
    else a * b
  }
}
