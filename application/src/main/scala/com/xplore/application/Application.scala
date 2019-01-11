package com.xplore.application

import com.xplore.application.config.{ApplicationConfig, SyncConfigLoader}
import com.xplore.server.Server
import com.xplore.server.Server.Handle
import com.xplore.server.akka.AkkaServer
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
    val server: Server[Future] = new AkkaServer(config.server.akka)
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

  log.info("Enter `Q` to terminate the app..")

  while (StdIn.readLine() != null) {}

  log.info("Application terminating..")

  sys.exit()
}
