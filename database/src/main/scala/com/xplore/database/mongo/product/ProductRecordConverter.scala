package com.xplore.database.mongo.product

import com.xplore.database.RecordConverter
import com.xplore.domain.{Product, Size}
import org.bson.types.ObjectId

class ProductRecordConverter extends RecordConverter[Product, ProductRecord] {

  override def toRecord(p: Product) = ProductRecord(ObjectId.get(), p.id, p.category.underlying, p.brand.underlying, p.colour.underlying, p.size.region.underlying, p.size.value, p.description)
  override def toEntity(r: ProductRecord) = Product(r.id, r.category, r.brand, r.colour, Size(r.size_region, r.size_value), r.description)
}

object ProductRecordConverter {

  def apply(): ProductRecordConverter = new ProductRecordConverter()
}