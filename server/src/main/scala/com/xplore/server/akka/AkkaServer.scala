package com.xplore.server.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.xplore.server.Server
import com.xplore.server.Server.Handle
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}

class AkkaServer(serverConfig: AkkaServerConfig) extends Server[Future] {

  val log: Logger = LoggerFactory.getLogger(getClass)

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val r: Route = get {
    complete("Hello")
  }

  override def run(): Future[Server.Handle[Future]] = {
    log.info("Server starting..")

    Http()
      .bindAndHandle(r, serverConfig.interface, serverConfig.port)
      .map { binding ⇒
        Handle[Future] {
          for {
            _ <- binding.terminate(5.seconds)
            _ = materializer.shutdown()
            _ ← system.terminate()
          } yield log.info("Server terminated..")
        }
      }
  }
}
