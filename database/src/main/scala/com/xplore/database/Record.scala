package com.xplore.database

import java.util.UUID

import com.xplore.database.Record.ID

trait Record {
  val id: ID
}

object Record {
  type ID = UUID
}