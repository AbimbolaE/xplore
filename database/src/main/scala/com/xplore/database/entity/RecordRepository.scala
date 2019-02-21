package com.xplore.database.entity

import com.xplore.database.entity.Record.ID

import scala.language.higherKinds

trait RecordRepository[F[_], R <: Record] {

  def create(record: R): F[Option[R]]
  def read(): F[Seq[R]]
  def read(id: ID): F[Option[R]]
  def update(record: R): F[Option[R]]
  def delete(id: ID): F[Option[ID]]
}
