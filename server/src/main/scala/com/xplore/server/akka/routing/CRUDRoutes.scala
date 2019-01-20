package com.xplore.server.akka.routing

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller
import com.xplore.domain.Repository
import com.xplore.server.akka.directives.PayloadValidator

import scala.concurrent.Future

abstract class CRUDRoutes[Payload: FromRequestUnmarshaller, Entity](context: String)
                                                                   (validator: PayloadValidator[Payload, Entity], repository: Repository[Future, Entity]) {

  val route: Route = path(context) {
    post {
      entity(as[Payload]) { payload ⇒
        validator.validate(payload) { entity =>
          onSuccess(repository.create(entity)) { uuid ⇒
            complete(uuid.toString)
          }
        }
      }
    }
  }
}