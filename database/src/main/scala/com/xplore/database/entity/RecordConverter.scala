package com.xplore.database.entity

trait RecordConverter[E, R <: Record] {

  def asNew(entity: E): R
  def asExisting(record: R): E
  def asUpdated(from: R, to: R): R
}
