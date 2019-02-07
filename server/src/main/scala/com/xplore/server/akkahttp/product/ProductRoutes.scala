package com.xplore.server.akkahttp.product

import akka.http.scaladsl.marshalling.ToResponseMarshaller
import com.xplore.database.mongo.product.ProductRecord
import com.xplore.database.{RecordConverter, Repository}
import com.xplore.domain.Product
import com.xplore.server.akkahttp.JsonSupport._
import com.xplore.server.akkahttp.{CRUDRoutes, PayloadValidator}
import com.xplore.server.payload.ProductPayload

import scala.concurrent.Future

class ProductRoutes(validator: PayloadValidator[ProductPayload, Product], converter: RecordConverter[Product, ProductRecord], repository: Repository[Future, ProductRecord])
  extends CRUDRoutes("products")(validator, converter, repository) {

  implicit val marshaller: ToResponseMarshaller[Product] = implicitly[ToResponseMarshaller[Product]]
}

object ProductRoutes {

  def apply(converter: RecordConverter[Product, ProductRecord], repository: Repository[Future, ProductRecord]) =
    new ProductRoutes(ProductPayloadValidator(), converter, repository)
}