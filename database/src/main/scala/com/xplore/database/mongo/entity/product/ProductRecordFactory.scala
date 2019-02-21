package com.xplore.database.mongo.entity.product

import com.xplore.database.entity.RecordFactory
import com.xplore.database.mongo.identifier.ObjectIdGenerator
import com.xplore.domain.identifier.IdGenerator
import com.xplore.domain.Product
import org.bson.types.ObjectId

class ProductRecordFactory(generator: IdGenerator[ObjectId]) extends RecordFactory[Product, ProductRecord] {

  override def create(p: Product): ProductRecord = ProductRecord(
    generator.newId,
    p.id,
    p.category.underlying,
    p.brand.underlying,
    p.colour.underlying,
    p.size.region.underlying,
    p.size.value,
    p.description
  )
}

object ProductRecordFactory {

  def apply(): ProductRecordFactory = new ProductRecordFactory(ObjectIdGenerator())
}
