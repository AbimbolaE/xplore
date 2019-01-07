ThisBuild / name := "xplore"
ThisBuild / version := "0.1"
ThisBuild / scalaVersion := "2.12.8"

lazy val xplore = project in file(".")

lazy val `xplore-domain` = project in file("domain")

lazy val `xplore-database` = project in file("database")
