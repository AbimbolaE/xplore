package com.xplore.domain

import java.util.UUID

import scala.language.higherKinds

trait Repository[F[_], A] {

  def create(entity: A): F[UUID]
  def read(id: UUID): F[A]
  def read(): F[Seq[A]]
  def update(id: UUID, entity: A): F[UUID]
  def delete(id: UUID): F[UUID]
}
