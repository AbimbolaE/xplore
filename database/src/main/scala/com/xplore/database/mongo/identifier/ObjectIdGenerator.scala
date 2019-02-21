package com.xplore.database.mongo.identifier

import com.xplore.domain.identifier.IdGenerator
import org.bson.types.ObjectId

class ObjectIdGenerator extends IdGenerator[ObjectId] {

  override def newId: ObjectId = ObjectId.get()
}

object ObjectIdGenerator {

  def apply(): ObjectIdGenerator = new ObjectIdGenerator()
}