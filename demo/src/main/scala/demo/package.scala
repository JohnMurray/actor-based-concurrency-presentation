import akka.actor._

import scala.collection.breakOut

package object demo {
  implicit val actorSystem = ActorSystem("demo-system")

  def isPrime(n: Int) = (2 until n) forall (n % _ != 0)

  def circular[A](a: Seq[A]): Stream[A] = {
    val repeat = a.toStream
    def b: Stream[A] = repeat #::: b
    b
  }
}
