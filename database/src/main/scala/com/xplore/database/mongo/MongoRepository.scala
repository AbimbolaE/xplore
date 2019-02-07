package com.xplore.database.mongo

import com.xplore.database.Record.ID
import com.xplore.database.{Record, Repository}
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{MongoCollection, MongoDatabase, MongoException}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

abstract class MongoRepository[R <: Record : ClassTag](collectionName: String, database: MongoDatabase) extends Repository[Future, R] {

  private val logger: Logger = LoggerFactory.getLogger(getClass)
  private val collection: MongoCollection[R] = database.getCollection[R](collectionName)

  override def create(record: R): Future[Option[R]] = {
    collection
      .insertOne(record)
      .toFuture()
      .map(_ ⇒ Option(record))
      .recover {
        case ex: MongoException =>
          logger.warn(s"Failed to create an record in $collectionName", ex)
          Option.empty[R]
      }
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
      .map(_.headOption)
  }

  override def update(record: R): Future[Option[R]] = {
    collection
      .findOneAndReplace(equal("id", record.id), record)
      .toFuture()
      .map(_ ⇒ Option(record))
  }

  override def delete(id: ID): Future[ID] = {
    collection
      .deleteOne(equal("id", id))
      .toFuture()
      .map(_ ⇒ id)
      .recover {
        case ex: MongoException =>
          logger.warn(s"Failed to delete an record in $collectionName", ex)
          id
      }
  }
}


