package com.xplore.database.mongo

import org.mongodb.scala.{MongoClient, MongoClientSettings, MongoDatabase, ServerAddress}

import scala.collection.JavaConverters._

class MongoDatabaseFactory(config: MongoConfig, registryFactory: MongoCodecRegistryFactory) {

  def create(): MongoDatabase = {
    val address = new ServerAddress(config.host, config.port)

    val registry = registryFactory.create()

    val settings = MongoClientSettings
      .builder()
      .applyToClusterSettings { b ⇒ b.hosts(Seq(address).asJava) }
      .codecRegistry(registry)
      .build()

    val client = MongoClient(settings)

    client.getDatabase(config.database)
  }
}

object MongoDatabaseFactory {

  def apply(config: MongoConfig): MongoDatabaseFactory = new MongoDatabaseFactory(config, MongoCodecRegistryFactory())
}
