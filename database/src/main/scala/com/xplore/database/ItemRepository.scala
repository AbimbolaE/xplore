package com.xplore.database

import java.util.UUID

import com.xplore.domain.{Item, Repository}

import scala.concurrent.Future

class ItemRepository extends Repository[Future, Item] {

  override def create(entity: Item): Future[UUID] = ???
  override def read(id: UUID): Future[Item] = ???
  override def read(): Future[Seq[Item]] = ???
  override def update(id: UUID, entity: Item): Future[UUID] = ???
  override def delete(id: UUID): Future[UUID] = ???
}

object ItemRepository {

  def apply() = new ItemRepository()
}
