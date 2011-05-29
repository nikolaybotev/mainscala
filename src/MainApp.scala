
object Other {
	
	var x: Int = 20
	
	case class Nested() {
		def fun() { return x }
		def fun1(): Long = { return x }

    def fun2(): Int = { return x }

    val fun23 = () => { x }
  }

}

trait YTrait {
}

object MainApp {
  def main(args : Array[String]) : Unit = {
	  println("Yesss")
	  println(System.getProperty("os.name"))
	  
	  var x = Other.Nested();
  }
}

object MatchTest1 extends App {
  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }
  println(matchTest(3))
}

object MatchTest2 extends App {
  def matchTest(x: Any): Any = x match {
    case 1 => "one"
    case "two" => 2
    case y: Int => "scala.Int"
  }
  println(matchTest("two"))
}

object MatchTest23 extends App {
  def matchTest(x: Any): Any = x match {
    case 1 => "one"
    case "two" => 2
    case y: Int => "scala.Int"
  }
  println(matchTest("two"))
}

abstract class Term
case class Var(name: String) extends Term
case class Fun(arg: String, body: Term) extends Term
case class Appl(f: Term, v: Term) extends Term

object TermTest extends App {
  def printTerm(term: Term) {
    term match {
      case Var(n) =>
        print(n)
      case Fun(x, b) =>
        print("^" + x + ".")
        printTerm(b)
      case Appl(f, v) =>
        Console.print("(")
        printTerm(f)
        print(" ")
        printTerm(v)
        print(")")
    }
  }
  def isIdentityFun(term: Term): Boolean = term match {
    case Fun(x, Var(y)) => {
      if (x == y) true;
      false
    }
    case Fun(x, Appl(Appl(Fun(z, Var(y)), _), Var(d))) if x == y && z == d => true
    case _ => false
  }
  val id = Fun("z", Appl(Appl(Fun("x", Var("z")), Var("t")), Var("x")))
  val t = Fun("x", Fun("y", Appl(Var("x"), Var("y"))))
  printTerm(t)
  println
  println(isIdentityFun(id))
  println(isIdentityFun(t))
}
