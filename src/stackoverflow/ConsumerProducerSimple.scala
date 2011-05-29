package stackoverflow

object ConsumerProducerSimple extends scala.App {

import scala.actors.Actor
import scala.actors.Actor._

case class Request(sender : Actor, payload : String)
case class Result(result : String)

def submit(e: Request) = actor {
  var counter: Int = 0
  react {
    case Request(sender, payload) =>
      //println("request to consumer " + n + " with " + payload)
      // some silly computation so the process takes awhile
      val result = ((payload + payload + payload) map {case '0' => 'X'; case '1' => "-"; case c => c}).mkString
      counter += result.length
      sender ! Result(result)
      //println("consumer " + n + " is done processing " + result )
    }
  } ! e

actor {

	println("Sending messages ...")

	val start = System.currentTimeMillis

	// a little test loop - note that it's not doing anything with the results or telling the coordinator to stop
	for (i <- 0 to 1000000) submit(Request(self, i.toString))

	println("All msgs sent.")

	for (i <-0 to 1000000) receive {
	  case Result(s) => if (i % 100000 == 0) println(" progress " + i + " " + s) 
	  case a@_ => println("Unknonw " + a)
	}

	println("Time to finish: " + (System.currentTimeMillis - start))

}

}




