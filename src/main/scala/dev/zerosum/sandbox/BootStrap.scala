package dev.zerosum.sandbox

import dev.zerosum.sandbox.server.HttpServer
import wvlet.airframe.bind

trait BootStrap {

  private lazy val server = bind[HttpServer]

  def start(): Unit = {
    server.startServer("0.0.0.0", 8080)
  }
}
