package com.xplore.server.akkahttp.entity.product

import com.xplore.database.mongo.entity.product.ProductRecord
import com.xplore.database.entity.{EntityRepository, RecordRepository}
import com.xplore.database.entity.product.ProductRepository
import com.xplore.domain.Product
import com.xplore.server.akkahttp.entity.EntityRoutes
import com.xplore.server.akkahttp.entity.JsonSupport._
import com.xplore.server.entity.product.{ProductRequest, ProductRequestValidator, ProductResponse, ProductResponseFactory}
import com.xplore.server.entity.{RequestValidator, ResponseFactory}

import scala.concurrent.Future

class ProductRoutes(validator: RequestValidator[ProductRequest, Product],
                    repository: EntityRepository[Future, Product, ProductRecord],
                    factory: ResponseFactory[Product, ProductResponse])
  extends EntityRoutes(rootPath = "products")(validator, repository, factory)

object ProductRoutes {

  def apply(repository: RecordRepository[Future, ProductRecord]): ProductRoutes = {
    new ProductRoutes(ProductRequestValidator(), ProductRepository(repository), ProductResponseFactory())
  }
}