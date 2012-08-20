object TestStrangeFunctions extends App {

  println("Hello")

  def getA: Int => Int = Array(1, 2, 3)

  val domains = List("abc", "dyf", "domain3", 2132, 4.5)

  val listed = domains map ("(" + _.toString + "!)") reduce (_ + "," + _)

  println(listed)

  println(null.isInstanceOf[Object])

}