package com.xplore.database.entity

import java.util.UUID

import com.xplore.database.entity.Record.ID

trait Record {
  val id: ID
}

object Record {
  type ID = UUID
}