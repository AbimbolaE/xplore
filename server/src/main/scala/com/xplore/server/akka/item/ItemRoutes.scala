package com.xplore.server.akka.item

import com.xplore.domain.{Item, Repository}
import com.xplore.server.akka.routing.CRUDRoutes
import com.xplore.server.akka.directives.PayloadValidator

import scala.concurrent.Future

class ItemRoutes(validator: PayloadValidator[ItemPayload, Item], repository: Repository[Future, Item])
  extends CRUDRoutes("items")(validator, repository)

object ItemRoutes {

  def apply(repository: Repository[Future, Item]) = new ItemRoutes(ItemPayloadValidator(), repository)
}