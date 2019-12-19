package dev.zerosum.sandbox.controller

import akka.http.scaladsl.server.Route

trait Controller {
  val route: Route
}
