package com.xplore.database

import java.util.UUID

import com.xplore.domain.{Product, Repository}

import scala.concurrent.Future

class ProductRepository extends Repository[Future, Product] {

  override def create(entity: Product): Future[UUID] = ???
  override def read(id: UUID): Future[Product] = ???
  override def read(): Future[Seq[Product]] = ???
  override def update(id: UUID, entity: Product): Future[UUID] = ???
  override def delete(id: UUID): Future[UUID] = ???
}

object ProductRepository {

  def apply() = new ProductRepository()
}
