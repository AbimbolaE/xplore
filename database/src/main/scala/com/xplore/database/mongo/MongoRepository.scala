package com.xplore.database.mongo

import com.mongodb.client.model.FindOneAndReplaceOptions
import com.xplore.database.entity.Record.ID
import com.xplore.database.entity.{Record, RecordRepository}
import org.mongodb.scala.MongoDatabase
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.ReturnDocument

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

abstract class MongoRepository[R <: Record : ClassTag](collectionName: String, database: MongoDatabase) extends RecordRepository[Future, R] {

  private val collection = database.getCollection[R](collectionName)
  private val updateOptions = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER)

  override def create(record: R): Future[Option[R]] = {
    collection
      .insertOne(record)
      .toFuture()
      .map { _ => Option(record) }
  }

  override def read(): Future[Seq[R]] = {
    collection
      .find()
      .toFuture()
  }

  override def read(id: ID): Future[Option[R]] = {
    collection
      .find(equal("id", id))
      .toFuture()
      .map { _.headOption }
  }

  override def update(record: R): Future[Option[R]] = {
    collection
      .findOneAndReplace(equal("id", record.id), record, updateOptions)
      .toFuture()
      .map { Option.apply[R] }
  }

  override def delete(id: ID): Future[Option[ID]] = {
    collection
      .deleteOne(equal("id", id))
      .toFuture()
      .map { result =>
        if (result.getDeletedCount == 1) Some(id)
        else None
      }
  }
}


