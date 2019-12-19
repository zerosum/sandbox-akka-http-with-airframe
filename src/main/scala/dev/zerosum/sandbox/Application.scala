package dev.zerosum.sandbox

import akka.actor.ActorSystem
import dev.zerosum.sandbox.server.{HttpServer, Routes}
import org.slf4j.bridge.SLF4JBridgeHandler
import wvlet.airframe._

object Application {
  def main(args: Array[String]): Unit = {
    init_jul_to_slf4j()

    val design = newDesign.withProductionMode
      .bind[ActorSystem].toInstance(ActorSystem("sandbox"))
      .bind[HttpServer].toSingleton
      .bind[Routes].toSingleton
      .add(base.design)
      .add(controller.design)

    // startup server with airframe session
    design.build[BootStrap](_.start())
  }

  /** airframeのloggingが[[java.util.logging]]に依存しているため, slf4jへのブリッジをする
    *
    * @see [[https://wvlet.org/airframe/docs/airframe-log.html#why-it-uses-javautillogging-instead-of-slf4j]]
    * @see [[http://www.slf4j.org/legacy.html#jul-to-slf4j]]
    * @see [[org.slf4j.bridge.SLF4JBridgeHandler]]
    */
  private[this] def init_jul_to_slf4j(): Unit = {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
  }

}
