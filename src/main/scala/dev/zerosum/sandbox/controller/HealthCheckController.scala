package dev.zerosum.sandbox.controller

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

class HealthCheckController extends Controller {

  override val route: Route = pathEndOrSingleSlash {
    // GET / # for health check
    complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "alive"))
  }
}
