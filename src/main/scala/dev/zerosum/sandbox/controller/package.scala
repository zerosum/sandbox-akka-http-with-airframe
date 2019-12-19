package dev.zerosum.sandbox

import wvlet.airframe._

package object controller {

  val design: Design =
    newDesign
      .bind[HealthCheckController].toSingleton
}
