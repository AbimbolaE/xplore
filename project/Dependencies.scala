import sbt._

object Dependencies {

  object Akka {
    private val akkaHttpVersion = "10.1.7"

    val `akka-http` = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
    val `akka-http-spray-json` = "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
    val `akka-stream` = "com.typesafe.akka" %% "akka-stream" % "2.5.19"
  }

  object Cats {
    val `cats-core` = "org.typelevel" %% "cats-core" % "1.6.0"
  }
  
  object Mongo {
    val `mongo-scala-driver` = "org.mongodb.scala" %% "mongo-scala-driver" % "2.5.0"
  }

  object PureConfig {
    val pureconfig = "com.github.pureconfig" %% "pureconfig" % "0.10.1"
  }

  object Slf4j {
    private val version = "1.7.7"

    val `slf4j-api` = "org.slf4j" % "slf4j-api" % version
    val `slf4j-simple` = "org.slf4j" % "slf4j-simple" % version
  }

  object Typesafe {
    val typesafeConfig = "com.typesafe" % "config" % "1.3.3"
  }

  object Webjars {
    val jquery = "org.webjars" % "jquery" % "3.0.0"
    val bootstrap = "org.webjars" % "bootstrap" % "4.2.1"
    val angularjs = "org.webjars" % "angularjs" % "1.7.5"
    val `webjars-locator` = "org.webjars" % "webjars-locator" % "0.34"
  }
}
