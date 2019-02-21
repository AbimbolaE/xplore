package com.xplore.database.mongo.entity.product

import java.util.UUID

import com.xplore.database.entity.Record
import org.bson.types.ObjectId

case class ProductRecord(_id: ObjectId, id: UUID, category: String, brand: String, colour: String, size_region: String, size_value: Double, description: Map[String, String]) extends Record
