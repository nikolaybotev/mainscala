
object ActorProxy extends scala.App {

  import scala.actors._
  import scala.actors.Actor._
  import scala.collection.mutable._

  /**
   * Accepts an actor and a message queue size, and 
   * returns a proxy that drops messages if the queue
   * size of the target actor exceeds the given queue size.
   */
  def boundActorQueue(target: AbstractActor, maxQueueLength: Int) = actor {
    val queue = new Queue[Tuple2[Any, OutputChannel[Any]]]
    var lastMessageSender: Option[OutputChannel[Any]] = None

    def replyHandler(response: Any) {
      if (lastMessageSender.get != null) {
        lastMessageSender.get ! response
      }
      if (queue.isEmpty) {
    	lastMessageSender = None
      } else {
        val (message, messageSender) = queue.dequeue
        forwardMessage(message, messageSender)
      }
    }

    def forwardMessage(message: Any, messageSender: OutputChannel[Any]) = {
      lastMessageSender = Some(messageSender)
      target !! (message, { case response => replyHandler(response) })
    }
    
    self.trapExit = true
    link(target)

    loop {
      react {
        case Exit(`target`, reason) => exit(reason)
        case message =>
          if (lastMessageSender == None) {
            forwardMessage(message, sender)
          } else {
            queue.enqueue((message, sender))
            // Restrict the queue size
            if (queue.length > maxQueueLength) {
            	val dropped = queue.dequeue
            	println("!!!!!!!! Dropped message " + dropped._1)
            }
          }
      }
    }
  }

  // Helper method
  def boundActor(maxQueueLength: Int)(body: => Unit) = boundActorQueue(actor(body), maxQueueLength)

  val adder = actor {
    loop {
      react {
        case x: Int => println(" Computing " + x); reply(x+2)
        case Exit => println("Exiting"); exit
      }
    }
  }

  actor {
    for (i <- 1 to 5) {
      println("Sending " + i)
      adder !! (i, { case answer => println("Computed " + i + " -> " + answer) })
    }

    println("Sending Exit")
    adder !! Exit
  }

}
