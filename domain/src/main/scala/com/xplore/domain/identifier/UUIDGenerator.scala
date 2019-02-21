package com.xplore.domain.identifier

import java.util.UUID

class UUIDGenerator extends IdGenerator[UUID] {

  override def newId: UUID = UUID.randomUUID()
}

object UUIDGenerator {

  def apply(): UUIDGenerator = new UUIDGenerator()
}