package com.xplore.server.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.xplore.server.Server
import com.xplore.server.Server.Handle
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}

class AkkaServer(config: AkkaServerConfig, router: Router) extends Server[Future] {

  val log: Logger = LoggerFactory.getLogger(getClass)

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def run(): Future[Server.Handle[Future]] = {
    log.info("Server starting..")

    Http()
      .bindAndHandle(router.routes, config.interface, config.port)
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
