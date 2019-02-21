package com.xplore.database.entity

trait RecordFactory[E, R <: Record] {

  def create(entity: E): R
}
