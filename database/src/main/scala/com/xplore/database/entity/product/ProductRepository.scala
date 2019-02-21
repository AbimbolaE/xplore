package com.xplore.database.entity.product

import cats.instances.future._
import com.xplore.database.entity.{EntityRepository, RecordConverter, RecordRepository}
import com.xplore.database.mongo.entity.product.{ProductRecord, ProductRecordConverter}
import com.xplore.domain.Product

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProductRepository(converter: RecordConverter[Product, ProductRecord], repository: RecordRepository[Future, ProductRecord]) extends EntityRepository(converter, repository)

object ProductRepository {

  def apply(repository: RecordRepository[Future, ProductRecord]): ProductRepository = new ProductRepository(ProductRecordConverter(), repository)
}
