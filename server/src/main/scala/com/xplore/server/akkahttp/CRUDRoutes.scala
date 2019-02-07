package com.xplore.server.akkahttp

import akka.http.scaladsl.marshalling.{ToResponseMarshaller => Marshaller}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.{FromRequestUnmarshaller => Unmarshaller}
import com.xplore.database.{Record, RecordConverter, Repository}

import scala.concurrent.Future

abstract class CRUDRoutes[P: Unmarshaller, E: Marshaller, R <: Record](context: String)
                                                                      (validator: PayloadValidator[P, E],
                                                                       converter: RecordConverter[E, R],
                                                                       repository: Repository[Future, R]) {

  val route: Route = {
    path(context) {
      post {
        entity(as[P]) { payload ⇒
          validator.validate(payload) { entity =>
            val record = converter.toRecord(entity)

            onSuccess(repository.create(record)) { maybeRecord ⇒

              maybeRecord
                .map(converter.toEntity)
                .fold { complete(NotFound) } { complete(_) }
            }
          }
        }
      }
    }
  }
}