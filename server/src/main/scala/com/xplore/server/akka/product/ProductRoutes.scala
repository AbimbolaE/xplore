package com.xplore.server.akka.product

import com.xplore.domain.{Product, Repository}
import com.xplore.server.akka.JsonProtocol._
import com.xplore.server.akka.{CRUDRoutes, PayloadValidator}
import com.xplore.server.payload.ProductPayload

import scala.concurrent.Future

class ProductRoutes(validator: PayloadValidator[ProductPayload, Product], repository: Repository[Future, Product])
  extends CRUDRoutes("product")(validator, repository)

object ProductRoutes {

  def apply(repository: Repository[Future, Product]) = new ProductRoutes(ProductPayloadValidator(), repository)
}