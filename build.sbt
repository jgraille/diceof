import play.sbt.PlayImport.*

name := "DiceOf"
maintainer := "Jean-Baptiste Graille <jbgraille@gmail.com>"
version := "1.0.0"
scalaVersion := "2.13.16"

enablePlugins(PlayScala)
// enablePlugins(GatlingPlugin) uncomment to run gatling tests locally
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-parser-combinators" % "always"

val playVersion = "3.0.7"
val pekkoVersion = "1.0.1"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "3.10.3" % "test"
libraryDependencies += "io.gatling" % "gatling-test-framework" % "3.10.3" % "test"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"

libraryDependencies ++= Seq(
  guice,
  "org.playframework" %% "play" % playVersion,
  "org.playframework" %% "play-pekko-http-server" % playVersion,
  "org.apache.pekko" %% "pekko-actor-typed" % pekkoVersion,
  "org.apache.pekko" %% "pekko-stream" % pekkoVersion,
  "org.apache.pekko" %% "pekko-slf4j" % pekkoVersion
)