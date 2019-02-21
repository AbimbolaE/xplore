package com.xplore.database.entity

import cats.Monad
import com.xplore.database.entity.Record.ID

import scala.language.higherKinds

abstract class EntityRepository[F[_]: Monad, E, R <: Record](converter: RecordConverter[E, R], repository: RecordRepository[F, R]) {

  def create(entity: E): F[Option[E]] = {
    val record = converter.asNew(entity)
    val eventualMaybeRecord = repository.create(record)

    Monad[F]
      .map(eventualMaybeRecord) { maybeRecord ⇒
        maybeRecord
          .map(converter.asExisting)
      }
  }

  def read(): F[Seq[E]] = {
    val eventualRecords = repository.read()

    Monad[F]
      .map(eventualRecords) { records ⇒
        records.map(converter.asExisting)
      }
  }

  def read(id: ID): F[Option[E]] = {
    val eventualMaybeRecord = repository.read(id)

    Monad[F]
      .map(eventualMaybeRecord) { maybeRecord ⇒
        maybeRecord
          .map(converter.asExisting)
      }
  }

  def update(entity: E): F[Option[E]] = {
    val newRecord = converter.asNew(entity)
    val eventualMaybeExistingRecord = repository.read(newRecord.id)

    Monad[F]
      .flatMap(eventualMaybeExistingRecord) { maybeExistingRecord ⇒
        maybeExistingRecord
          .fold(Monad[F].pure(Option.empty[E])) { existingRecord ⇒

            val record = converter.asUpdated(existingRecord, newRecord)
            val eventualMaybeRecord = repository.update(record)

            Monad[F]
              .map(eventualMaybeRecord) { maybeRecord ⇒
                maybeRecord
                  .map(converter.asExisting)
              }
          }
      }
  }

  def delete(id: ID): F[Option[ID]] = {
    repository.delete(id)
  }
}
