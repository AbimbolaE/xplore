package com.xplore.database

import com.xplore.database.Record.ID

trait Repository[F[_], R <: Record] {

  def create(record: R): F[Option[R]]
  def read(): F[Seq[R]]
  def read(id: ID): F[Option[R]]
  def update(record: R): F[Option[R]]
  def delete(id: ID): F[ID]
}
