package dev.zerosum.sandbox

import java.time._

import wvlet.airframe._

package object base {

  private[base] lazy val defaultOffset = ZoneOffset.UTC
  private[base] lazy val defaultClock  = Clock.system(defaultOffset)

  val design: Design = newDesign
    .bind[Clock].toInstance(defaultClock)
}
