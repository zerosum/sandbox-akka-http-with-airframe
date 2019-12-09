package dev.zerosum.sandbox

import akka.Done
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{HttpApp, Route}

import scala.concurrent.{ExecutionContext, Future, Promise, blocking}
import scala.language.postfixOps

object HttpServer extends HttpApp {
  override protected def routes: Route =
    path("hello") {
      get {
        complete(
          HttpEntity(ContentTypes.`text/html(UTF-8)`,
                     "<h1>Say hello to akka-http</h1>"))
      }
    }

  override protected def waitForShutdownSignal(system: ActorSystem)(
      implicit ec: ExecutionContext): Future[Done] = {

    CoordinatedShutdown.get(system)

    val promise = Promise[Done]()
    sys.addShutdownHook {
      promise.trySuccess(Done)
    }
    Future {
      blocking {
        while (true) {
          // Ain't you scare of infinite loop?
        }
        promise.trySuccess(Done)
      }
    }
    promise.future
  }
}
