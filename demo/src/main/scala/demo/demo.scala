package demo

import akka.actor.ActorDSL._
import akka.actor._

object DemoApp {
  lazy val primeFinder = actor(new Act {

    var workerPool = List[ActorRef]()

    whenStarting {
      (1 to 5).foreach { _ => 
        val workerActor = actor(context)(new Act{
          become {
            case value: Int => if (isPrime(value)) {
              sender() ! {value -> true}
            }
          }
        })
        workerPool ::= workerActor
      }
    }

    become {
      case range: Range =>
        val workers = circular(workerPool).iterator
        range.foreach { number =>
          workers.next ! number
        }

      case (value: Int, true) =>
        println(value)
    }
  })

  // primeFinder ! (1 to 100)

}
