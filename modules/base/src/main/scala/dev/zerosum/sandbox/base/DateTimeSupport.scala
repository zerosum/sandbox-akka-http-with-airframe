package dev.zerosum.sandbox.base

import java.time._

import wvlet.airframe._

trait DateTimeSupport {

  private lazy val clock = bind[Clock]

  def now: OffsetDateTime = OffsetDateTime.now(clock)
}
