package com.xplore.application

import com.xplore.application.config.SyncConfigLoader
import com.xplore.database.mongo.entity.product.ProductRecordRepository
import com.xplore.database.mongo.{MongoConfig, MongoDatabaseFactory}
import com.xplore.server.Server.Handle
import com.xplore.server.akkahttp.{AkkaConfig, AkkaServer}
import org.mongodb.scala.MongoDatabase
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.StdIn
import scala.sys.ShutdownHookThread
import scala.util.{Failure, Success}

object Application extends App {

  val log = LoggerFactory.getLogger(getClass)

  log.info("Application starting..")

  val fetchConfig = () ⇒ {
    val configLoader = new SyncConfigLoader()
    val configAttempt = configLoader.load()
    Future.fromTry(configAttempt)
  }

  val initialiseDatabase = (config: MongoConfig) ⇒ Future {
    val factory = MongoDatabaseFactory(config)
    val database = factory.create()
    database
  }

  val startServer = (config: AkkaConfig, database: MongoDatabase) ⇒ {
    val server = AkkaServer(config, ProductRecordRepository(database))
    server.run()
  }

  val addShutdownHook = (handle: Handle[Future]) ⇒ {
    sys.addShutdownHook {
      Await.result(handle.shutdown(), 5.seconds)
      log.info("Application terminated..")
    }
  }

  val eventualStartup: Future[ShutdownHookThread] = {
    for {
      config ← fetchConfig()
      database ← initialiseDatabase(config.database.mongo)
      handle ← startServer(config.server.akka, database)
    } yield addShutdownHook(handle)
  }

  eventualStartup
    .andThen {
      case Success(_) ⇒
        log.info("Application started..")
      case Failure(ex) ⇒
        log.info("Application startup failed..", ex)
        sys.exit(1)
    }

  log.info("Press RETURN to terminate the app..")

  val stillRunning = () => {
    val input = StdIn.readLine()
    input != sys.props("line.separator") && input != ""
  }

  while (stillRunning()) {}

  log.info("Application terminating..")

  sys.exit()
}
