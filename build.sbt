lazy val baseSettings = Seq(
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq("-unchecked",
                        "-deprecation",
                        "-feature",
                        "-target:jvm-1.8"),
  libraryDependencies ++= {
    val airframeVersion = "19.12.3"
    val slf4jVersion    = "1.7.29"

    Seq(
      "org.wvlet.airframe"         %% "airframe"       % airframeVersion,
      "ch.qos.logback"             % "logback-classic" % "1.2.3",
      "org.slf4j"                  % "slf4j-api"       % slf4jVersion,
      "org.slf4j"                  % "jul-to-slf4j"    % slf4jVersion,
      "com.typesafe.scala-logging" %% "scala-logging"  % "3.9.2",
      "org.scalactic"              %% "scalactic"      % "3.1.0",
      "org.scalatest"              %% "scalatest"      % "3.1.0" % Test
    )
  }
)

lazy val base = (project in file("modules/base"))
  .settings(baseSettings)

lazy val domain = (project in file("modules/domain"))
  .dependsOn(base)
  .settings(baseSettings)

lazy val interface = (project in file("modules/interface"))
  .dependsOn(base, domain)
  .settings(
    baseSettings,
    libraryDependencies ++= {
      val scalikejdbcVersion = "3.4.0"

      Seq(
        "org.scalikejdbc" %% "scalikejdbc"                      % scalikejdbcVersion,
        "org.scalikejdbc" %% "scalikejdbc-config"               % scalikejdbcVersion,
        "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion,
        "com.zaxxer"      % "HikariCP"                          % "3.4.1",
        "com.h2database"  % "h2"                                % "1.4.200"
      )
    }
  )
lazy val app = (project in file("."))
  .dependsOn(base, domain, interface)
  .enablePlugins(JavaAppPackaging)
  .settings(
    baseSettings,
    name := "sandbox-akka-http-with-airframe",
    version := "0.1",
    libraryDependencies ++= {
      val akkaVersion     = "2.6.1"
      val akkaHttpVersion = "10.1.11"

      Seq(
        "com.typesafe.akka" %% "akka-actor"  % akkaVersion,
        "com.typesafe.akka" %% "akka-stream" % akkaVersion,
        "com.typesafe.akka" %% "akka-http"   % akkaHttpVersion
      )
    }
  )
  .settings(
    packageName in Docker := "sandbox-akka-http",
    version in Docker := "latest",
    dockerRepository := Some("index.docker.io/z3r05um"),
    maintainer in Docker := "TAKAHASHI Osamu <info@zerosum.dev>",
    dockerExposedPorts := List(8080),
    dockerBaseImage := "adoptopenjdk/openjdk8:ubuntu",
    dockerCmd := Nil,
    mainClass in Compile := Some("dev.zerosum.sandbox.Application")
  )
