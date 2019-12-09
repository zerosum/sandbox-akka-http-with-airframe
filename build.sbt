name := "sandbox-akka-http-with-airframe"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions ++= Seq("-unchecked",
                      "-deprecation",
                      "-feature",
                      "-target:jvm-1.8")

val airframeVersion = "19.11.2"
val slf4jVersion    = "1.7.29"
val akkaVersion     = "2.6.1"
val akkaHttpVersion = "10.1.11"

libraryDependencies ++= Seq(
  "org.wvlet.airframe" %% "airframe"       % airframeVersion,
  "ch.qos.logback"     % "logback-classic" % "1.2.3",
  "org.slf4j"          % "slf4j-api"       % slf4jVersion,
  "org.slf4j"          % "jul-to-slf4j"    % slf4jVersion,
  "com.typesafe.akka"  %% "akka-actor"     % akkaVersion,
  "com.typesafe.akka"  %% "akka-stream"    % akkaVersion,
  "com.typesafe.akka"  %% "akka-http"      % akkaHttpVersion
)

enablePlugins(JavaAppPackaging)

packageName in Docker := "sandbox-akka-http"
version in Docker := "latest"
dockerRepository := Some("index.docker.io/z3r05um")
maintainer in Docker := "TAKAHASHI Osamu <info@zerosum.dev>"
dockerExposedPorts := List(8080)
dockerBaseImage := "adoptopenjdk/openjdk8:ubuntu"
dockerCmd := Nil
mainClass in Compile := Some("dev.zerosum.sandbox.BootStrap")
