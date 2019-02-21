package com.xplore.server.entity.product

import com.xplore.domain.Product
import com.xplore.server.entity.ResponseFactory

class ProductResponseFactory extends ResponseFactory[Product, ProductResponse] {

  override def create(entity: Product): ProductResponse = ProductResponse(
    entity.id,
    entity.brand.underlying,
    entity.category.underlying,
    entity.colour.underlying,
    entity.size.region.underlying,
    entity.size.value,
    entity.description
  )
}

object ProductResponseFactory {

  def apply(): ProductResponseFactory = new ProductResponseFactory()
}