import Dependencies.Akka._
import Dependencies.Cats.`cats-core`
import Dependencies.Mongo.`mongo-scala-driver`
import Dependencies.PureConfig.pureconfig
import Dependencies.Slf4j._
import Dependencies.Typesafe.config

ThisBuild / name := "xplore"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val xplore = project in file(".")

lazy val `xplore-domain` = module("domain")

lazy val `xplore-database` = module("database")
  .dependsOn(`xplore-domain`)
  .settings(
    libraryDependencies ++= Seq(
      `mongo-scala-driver`
    )
  )

lazy val `xplore-server` = module("server")
  .dependsOn(`xplore-domain`)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      `akka-http`,
      `akka-http-spray-json`,
      `akka-stream`,
      `cats-core`
    )
  )

lazy val `xplore-application` = module("application")
  .dependsOn(`xplore-server`, `xplore-database`)
  .settings(commonSettings)
  .settings(
    fork := true,
    connectInput in run := true,
    libraryDependencies ++= Seq(
      config,
      pureconfig
    )
  )

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    `slf4j-api`,
    `slf4j-simple`
  )
)

def module(name: String) = Project(name, file(name))
