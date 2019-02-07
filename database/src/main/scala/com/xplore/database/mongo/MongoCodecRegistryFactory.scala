package com.xplore.database.mongo

import com.xplore.database.mongo.product.ProductRecord
import org.bson.UuidRepresentation
import org.bson.codecs.UuidCodec
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.{CodecProvider, CodecRegistries, CodecRegistry}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

class MongoCodecRegistryFactory {

  def create(): CodecRegistry = {
    val appProviders: Seq[CodecProvider] = Seq(classOf[ProductRecord])
    val appRegistry = fromProviders(appProviders: _*)
    val uuidRegistry: CodecRegistry = CodecRegistries.fromCodecs(new UuidCodec(UuidRepresentation.STANDARD))
    val codecRegistry = fromRegistries(appRegistry, uuidRegistry, DEFAULT_CODEC_REGISTRY)
    codecRegistry
  }
}

object MongoCodecRegistryFactory {

  def apply(): MongoCodecRegistryFactory = new MongoCodecRegistryFactory()
}