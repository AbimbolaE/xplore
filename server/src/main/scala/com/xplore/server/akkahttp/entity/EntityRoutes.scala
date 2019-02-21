package com.xplore.server.akkahttp.entity

import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.xplore.database.entity.{EntityRepository, Record}
import com.xplore.server.akkahttp.validation.RequestValidation._
import com.xplore.server.entity.{RequestValidator, ResponseFactory}
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.concurrent.Future

abstract class EntityRoutes[Req: RootJsonFormat, E, R <: Record, Res: RootJsonFormat](rootPath: String)
                                                                                     (validator: RequestValidator[Req, E],
                                                                                      repository: EntityRepository[Future, E, R],
                                                                                      factory: ResponseFactory[E, Res]) {

  val route: Route = {
    logRequestResult(s"/$rootPath" → Logging.InfoLevel) {
      pathPrefix(rootPath) {
        post {
          entity(as[Req]) { request ⇒
            validateNew(validator)(request) { entity ⇒
              onSuccess(repository.create(entity)) { maybeEntity ⇒
                maybeEntity
                  .map(factory.create)
                  .fold { complete(Conflict) } { res ⇒ complete(OK → res) }
              }
            }
          }
        } ~
        get {
          pathEnd {
            onSuccess(repository.read()) { entities ⇒
              val result = entities.map(factory.create)
              complete(OK → result)
            }
          }
        } ~
        get {
          path(JavaUUID) { id ⇒
            onSuccess(repository.read(id)) { maybeEntity ⇒
              maybeEntity
                .map(factory.create)
                .fold { complete(NotFound → "") } { res ⇒ complete(OK → res) }
            }
          }
        } ~
        put {
          path(JavaUUID) { id ⇒
            entity(as[Req]) { request ⇒
              validateExisting(validator)(request, id) { entity ⇒
                onSuccess(repository.update(entity)) { maybeEntity ⇒
                  maybeEntity
                    .map(factory.create)
                    .fold { complete(NotFound → "") } { res ⇒ complete(OK → res) }
                }
              }
            }
          }
        } ~
        delete {
          path(JavaUUID) { id ⇒
            onSuccess(repository.delete(id)) { maybeId ⇒
              maybeId
                .fold { complete(NotFound → "") } { _ ⇒ complete(OK) }
            }
          }
        }
      }
    }
  }
}