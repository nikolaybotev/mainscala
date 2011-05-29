package scalaz

object ScalazMonoid extends App {

  trait FoldLeftMonoid[F[_]] {
    def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
  }

  object FoldLeftMonoid {
    implicit object FoldLeftMonoidList extends FoldLeftMonoid[List] {
      def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
    }
  }

  trait Monoid[M] {
    def mappend(x: M, y: M): M
    def mzero: M
  }

  object Monoid {
    implicit object IntMonoid extends Monoid[Int] {
      def mappend(x: Int, y: Int): Int = x + y
      def mzero: Int = 0
    }
    implicit object StringMonoid extends Monoid[String] {
      def mappend(x: String, y: String): String = x + y
      def mzero: String = ""
    }
    object MultMonoid extends Monoid[Int] {
      def mappend(x: Int, y: Int): Int = x * y
      def mzero: Int = 1
    }
  }

  val multMonoid = new Monoid[Int] {
      def mappend(x: Int, y: Int): Int = x * y
      def mzero: Int = 1
  }

  trait Identity[A] {
    val value: A

    def |+|(b: A)(implicit m: Monoid[A]): A = m.mappend(value,b)
    
    
  }
  
  // Higher kind
  trait MA[M[_], A] {
    val value: M[A]
    
    def suma(implicit m: Monoid[A], fl: FoldLeftMonoid[M]): A = fl.foldLeft(value, m.mzero, m.mappend)
  }

  implicit def toIdent[A](a: A): Identity[A] = new Identity[A] {
    val value = a
  }
  
  implicit def toFoldable[M[_], A](ma: M[A]): MA[M, A] = new MA[M, A] {
    val value = ma
  }
  

  def sum[M[_], T](xs: M[T])(implicit m: Monoid[T], fl: FoldLeftMonoid[M]): T = fl.foldLeft(xs, m.mzero, m.mappend)
  def plus[T](a: T, b: T)(implicit m: Monoid[T]): T = m.mappend(a, b)

  p(plus(3, 4))
  p(3 |+| 4)

  def p(a: Any) { println("###> " + a) }

  println

  p(sum(List(1, 2, 3, 4)))
  p(sum(List(1, 2, 3, 4))(multMonoid, implicitly[FoldLeftMonoid[List]]))
  p(sum(List("a", "b", "c")))

  p(List(1, 2, 3, 4).suma)
  p(List(1, 2, 3, 4).suma(multMonoid, implicitly[FoldLeftMonoid[List]]))
  p(List("a", "b", "c").suma)
  
  val e = { () => 10 + y() }
  
  val y: ()=> Int = { () => 5 + e() }
  
  def test(wait: Boolean, one: Int, two: String) = {
    if (wait) println("yes " + one) else println("no " + two)
    // invalid: println(wait ? "a" : "2")
  }
  
  test(two = "a", one = 10, wait = false)

}
