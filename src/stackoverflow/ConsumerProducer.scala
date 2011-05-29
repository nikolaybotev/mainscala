package stackoverflow

object ConsumerProducerPool extends scala.App {

import scala.actors.Actor
import scala.actors.Actor._

case class Request(sender : Actor, payload : String)
case class Ready(sender : Actor)
case class Result(result : String)
case object Stop

def consumer(n : Int) = actor {
  var counter: Int = 0
  loop {
    react {
      case Ready(sender) => 
        sender ! Ready(self)
      case Request(sender, payload) =>
        //println("request to consumer " + n + " with " + payload)
        // some silly computation so the process takes awhile
        val result = ((payload + payload + payload) map {case '0' => 'X'; case '1' => "-"; case c => c}).mkString
        counter += result.length
        sender ! Result(result)
        //println("consumer " + n + " is done processing " + result )
      case Stop => exit
    }
  }
}

// a pool of 10 consumers
val consumers = for (n <- 0 to 20) yield consumer(n)

val coordinator = actor {
  loop {
     react {
        case msg @ Request(sender, payload) =>
           consumers foreach {_ ! Ready(self)}
           react {
              // send the request to the first available consumer
              case Ready(consumer) => consumer ! msg
           }
         case Stop => 
           consumers foreach {_ ! Stop} 
           exit
     }
  }
}

actor {

	println("Sending messages ...")

	val start = System.currentTimeMillis

	// a little test loop - note that it's not doing anything with the results or telling the coordinator to stop
	for (i <- 0 to 10000) coordinator ! Request(self, i.toString)
	coordinator ! Stop

	println("All msgs sent.")

	for (i <-0 to 10000) receive {
	  case Result(_) => if (i % 1000 == 0) println(" progress " + i) 
	  case a@_ => println("Unknonw " + a)
	}

	println("Time to finish: " + (System.currentTimeMillis - start))

}

}