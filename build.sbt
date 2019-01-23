import Dependencies.Akka._
import Dependencies.Mongo._
import Dependencies.PureConfig._
import Dependencies.Slf4j._
import Dependencies.Typesafe._
import Dependencies.Webjars._

ThisBuild / name := "xplore"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val xplore = module(".")
  .aggregate(`xplore-domain`, `xplore-database`, `xplore-web`, `xplore-server`, `xplore-application`)

lazy val `xplore-domain` = module("domain")

lazy val `xplore-database` = module("database")
  .dependsOn(`xplore-domain`)
  .settings(
    libraryDependencies ++= Seq(
      `mongo-scala-driver`
    )
  )

lazy val `xplore-web` = module("web")
  .settings(
    libraryDependencies ++= Seq(
      jquery,
      bootstrap,
      angularjs
    )
  )

lazy val `xplore-server` = module("server")
  .dependsOn(`xplore-domain`, `xplore-web`)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      `akka-http`,
      `akka-http-spray-json`,
      `akka-stream`,
      `webjars-locator`
    )
  )

lazy val `xplore-application` = module("application")
  .dependsOn(`xplore-server`, `xplore-database`)
  .settings(commonSettings)
  .settings(
    fork := true,
    connectInput in run := true,
    libraryDependencies ++= Seq(
      typesafeConfig,
      pureconfig
    )
  )

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    `slf4j-api`,
    `slf4j-simple`
  )
)

def module(name: String) = Project(if (name == ".") "xplore" else name, file(name))
