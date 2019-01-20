package com.xplore.application

import com.xplore.application.config.{ApplicationConfig, SyncConfigLoader}
import com.xplore.database.ItemRepository
import com.xplore.domain.validation.{RegionValidator, SizeValidator}
import com.xplore.server.Server.Handle
import com.xplore.server.akka.AkkaServer
import com.xplore.server.akka.routing.{ItemRoutes, Router}
import com.xplore.server.item.ItemPayloadValidator
import org.slf4j.LoggerFactory

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.StdIn
import scala.util.{Failure, Success}

object Application extends App {

  val log = LoggerFactory.getLogger(getClass)

  log.info("Application starting..")

  val fetchConfig = () ⇒ {
    val configLoader = new SyncConfigLoader()
    Future.fromTry(configLoader.load())
  }

  val startServer = (config: ApplicationConfig) ⇒ {
    val regionValidator = new RegionValidator
    val sizeValidator = new SizeValidator(regionValidator)
    val itemPayloadValidator = new ItemPayloadValidator(sizeValidator)
    val itemRepository = new ItemRepository
    val itemRoutes = new ItemRoutes(itemPayloadValidator, itemRepository)
    val router = new Router(itemRoutes)
    val server = new AkkaServer(config.server.akka, router)
    server.run()
  }

  val addShutdownHook = (handle: Handle[Future]) ⇒ {
    sys.addShutdownHook {
      Await.result(handle.shutdown(), 5.seconds)
      log.info("Application terminated..")
    }
    ()
  }

  val eventualStartup: Future[Unit] = for {
    config ← fetchConfig()
    handle ← startServer(config)
  } yield addShutdownHook(handle)

  eventualStartup
    .andThen {
      case Success(_) ⇒
        log.info("Application started..")
      case Failure(ex) ⇒
        log.info("Application startup failed..", ex)
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
