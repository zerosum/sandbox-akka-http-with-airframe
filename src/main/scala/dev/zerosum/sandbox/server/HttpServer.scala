package dev.zerosum.sandbox.server

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.settings.ServerSettings
import wvlet.airframe._

import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.util.{Failure, Success}

trait HttpServer {

  private[this] implicit val system: ActorSystem  = bind[ActorSystem]
  private[this] implicit val ec: ExecutionContext = system.dispatcher

  def startServer(host: String, port: Int): Unit = {
    val routes: Route = bind[Routes].routes
    val bindingFuture = Http().bindAndHandle(handler = routes,
                                             interface = host,
                                             port = port,
                                             settings = ServerSettings(system))

    bindingFuture.onComplete {
      case Success(binding) =>
        system.log.info("Server online at http://{}:{}/",
                        binding.localAddress.getHostName,
                        binding.localAddress.getPort)
      case Failure(cause) =>
        system.log.error(cause,
                         s"Error starting the server ${cause.getMessage}")
    }

    Await.ready(bindingFuture.flatMap(_ => waitForShutdownSignal(system)),
                Duration.Inf)

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(attempt => {
        system.log.info("Shutting down the server")
      })
  }

  private[this] def waitForShutdownSignal(system: ActorSystem)(
      implicit ec: ExecutionContext): Future[Done] = {

    val promise = Promise[Done]()
    sys.addShutdownHook {
      promise.trySuccess(Done)
    }
    promise.future
  }

}
