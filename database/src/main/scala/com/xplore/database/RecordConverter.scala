package com.xplore.database

trait RecordConverter[E, R <: Record] {

  def toRecord(entity: E): R
  def toEntity(record: R): E
}
