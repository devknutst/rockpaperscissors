import sbt.Resolver

name := """rockpaperscissors"""
organization := "home"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "net.ruippeixotog" %% "scala-scraper" % "2.0.0-RC2",
  "org.jsoup" % "jsoup" % "1.8.3",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test,
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.+",
  guice
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "home.controllers._"

// Adds additional packages into conf/routes
// make.sbt.routes.RoutesKeys.routesImport += "home.binders._"
