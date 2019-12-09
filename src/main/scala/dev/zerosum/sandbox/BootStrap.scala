package dev.zerosum.sandbox

object BootStrap {

  def main(args: Array[String]): Unit = {
    HttpServer.startServer("0.0.0.0", 8080)
  }
}
