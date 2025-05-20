import play.sbt.PlayImport.*

name := "DiceGame"
version := "1.0"
scalaVersion := "3.3.1"

enablePlugins(PlayScala)

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.19"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % "test"
libraryDependencies ++= Seq(
  guice
)

