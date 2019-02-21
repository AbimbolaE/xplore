package com.xplore.server.akkahttp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.xplore.database.entity.RecordRepository
import com.xplore.database.mongo.entity.product.ProductRecord
import com.xplore.server.Server
import com.xplore.server.Server.Handle
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

class AkkaServer(config: AkkaConfig, router: Router) extends Server[Future] {

  val log: Logger = LoggerFactory.getLogger(getClass)

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  override def run(): Future[Server.Handle[Future]] = {
    log.info("Server starting..")

    Http()
      .bindAndHandle(router.routes, config.host, config.port)
      .map { binding ⇒
        Handle[Future] {
          for {
            _ <- binding.terminate(5.seconds)
            _ = materializer.shutdown()
            _ ← system.terminate()
          } yield log.info("Server terminated..")
        }
      }
      .andThen {
        case Success(_) ⇒
          log.info("Server started..")
        case Failure(ex) ⇒
          log.warn("Server startup failed..", ex)
      }
  }
}

object AkkaServer {

  def apply(config: AkkaConfig, repository: RecordRepository[Future, ProductRecord]): AkkaServer = {
    new AkkaServer(config, Router(repository))
  }
}