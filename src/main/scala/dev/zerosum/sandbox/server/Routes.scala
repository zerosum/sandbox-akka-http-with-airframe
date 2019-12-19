package dev.zerosum.sandbox.server

import akka.http.scaladsl.server.{Directives, Route}
import dev.zerosum.sandbox.controller._
import wvlet.airframe._

trait Routes extends Directives {

  lazy val routes: Route = concatRoutes(
    bind[HealthCheckController]
  )

  private[this] def concatRoutes(controllers: Controller*): Route =
    controllers.map(_.route).reduce(_ ~ _)
}
