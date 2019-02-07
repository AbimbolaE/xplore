package com.xplore.database.mongo.product

import com.xplore.database.mongo.MongoRepository
import org.mongodb.scala.MongoDatabase

class ProductRepository(database: MongoDatabase) extends MongoRepository[ProductRecord](collectionName = "products", database)

object ProductRepository {

  def apply(database: MongoDatabase) = new ProductRepository(database)
}
