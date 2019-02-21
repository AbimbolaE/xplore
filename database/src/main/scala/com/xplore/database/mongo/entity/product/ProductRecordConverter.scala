package com.xplore.database.mongo.entity.product

import com.xplore.database.entity.{RecordConverter, RecordFactory}
import com.xplore.domain.{Product, Size}

class ProductRecordConverter(factory: RecordFactory[Product, ProductRecord]) extends RecordConverter[Product, ProductRecord] {

  override def asNew(p: Product): ProductRecord = factory.create(p)
  override def asExisting(r: ProductRecord): Product = Product(r.id, r.category, r.brand, r.colour, Size(r.size_region, r.size_value), r.description)
  override def asUpdated(from: ProductRecord, to: ProductRecord): ProductRecord = to.copy(_id = from._id)
}

object ProductRecordConverter {

  def apply(): ProductRecordConverter = new ProductRecordConverter(ProductRecordFactory())
}