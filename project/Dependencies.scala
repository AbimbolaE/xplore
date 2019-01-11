import sbt._
import sbt.librarymanagement.ModuleID

object Dependencies {

  object Akka {
    val `akka-http`: ModuleID = "com.typesafe.akka" %% "akka-http" % "10.1.7"
    val `akka-stream`: ModuleID = "com.typesafe.akka" %% "akka-stream" % "2.5.19"
  }

  object Cats {
    val `cats-core`: ModuleID = "org.typelevel" %% "cats-core" % "1.5.0"
  }

  object Slf4j {
    private val version = "1.7.5"

    val `slf4j-api` = "org.slf4j" % "slf4j-api" % version
    val `slf4j-simple` = "org.slf4j" % "slf4j-simple" % version
  }

  object Mongo {
    val `mongo-scala-driver` = "org.mongodb.scala" %% "mongo-scala-driver" % "2.5.0"
  }

  object PureConfig {
    val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.10.1"
  }
  
  object Typesafe {
    val config = "com.typesafe" % "config" % "1.3.2"
  }
}
