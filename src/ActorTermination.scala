object ActorTermination extends scala.App {
  import actors.Actor._
  actor {
    react {
      case x => println(x)
    }
  } ! "hello"
}
