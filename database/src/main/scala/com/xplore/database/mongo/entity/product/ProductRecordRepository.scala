package com.xplore.database.mongo.entity.product

import com.xplore.database.mongo.MongoRepository
import org.mongodb.scala.MongoDatabase

class ProductRecordRepository(database: MongoDatabase) extends MongoRepository[ProductRecord](collectionName = "products", database)

object ProductRecordRepository {

  def apply(database: MongoDatabase) = new ProductRecordRepository(database)
}
