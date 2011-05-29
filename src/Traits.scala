object Traits extends App {

  trait TestAbs {
    var test: Int
    def absmethod
    val immutable: String
  }
  
  class TestImpl {
    final var test = 10
    final def absmethod { println("hello") }
    final val immutable = "aide"
  }
  val x: String = ""
  1 to 10 foreach { println(_) }
  
  def upplay() = { 1 }
  def upplay2(): Long = { return 2 }
  def upplay3(): Int = { return 3 }
  
  val upplay4 = { println(44); 4 }
  def upplay5 = { println(55); 5 }
  
  println("upplay: " + upplay())
  println(upplay2())
  println(upplay3)
  println(upplay3 _)
  
  println("upplay4: " + upplay4)
  println("upplay4 _: " + upplay4 _)
  println("upplay5: " + upplay5)
  println("upplay5 _: " + upplay5 _)

  object Twice {
    def apply(x: Int): Int = x * 2
    def unapply(z: Int): Option[Int] = if (z % 2 == 0) Some(z / 2) else None
  }

  object TwiceTest extends App {
    val x = Twice(21)
    x match { case Twice(n) => Console.println(n) } // prints 21
  }
  
  trait TestT {
    def test { println("b") }
  }
  
  trait TestT2 {
    def test { println("NOOOOO") }
  }
  
  class TestTImpl extends TestT with TestT2 {
    override def test { super.test; println("a") }
  }
  
  new TestTImpl().test

  override def toString = "yes"

  def crazyfun(x1: Int, x2: Int, x3: Int, x4: Int, x5: Int, x6: Int, x7: Int, x8: Int, x9: Int, x10: Int,
      x11: Int, x12: Int, x13: Int, x14: Int, x15: Int, x16: Int, x17: Int, x18: Int, x19: Int, x20: Int
      ): Int = 10;

  val ref = (crazyfun _).tupled;

}