package com.xplore.server.akka.routing

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.xplore.domain.{Item, Repository}
import com.xplore.server.akka.validation.PayloadValidator
import com.xplore.server.item.ItemPayload

import scala.concurrent.Future

class ItemRoutes(validator: PayloadValidator[ItemPayload, Item], repository: Repository[Future, Item]) {

  val route: Route = path("items") {
    post {
      entity(as[ItemPayload]) { payload ⇒
        validator.validate(payload) { item =>
          onSuccess(repository.create(item)) { uuid ⇒
            complete(uuid.toString)
          }
        }
      }
    }
  }
}