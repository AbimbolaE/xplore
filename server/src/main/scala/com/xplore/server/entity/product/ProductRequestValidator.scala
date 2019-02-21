package com.xplore.server.entity.product

import java.util.UUID

import cats.data.{NonEmptyChain, Validated}
import com.xplore.domain.Product
import com.xplore.domain.identifier.{IdGenerator, UUIDGenerator}
import com.xplore.domain.validation.SizeValidator
import com.xplore.server.entity.RequestValidator

class ProductRequestValidator(validator: SizeValidator, generator: IdGenerator[UUID]) extends RequestValidator[ProductRequest, Product] {

  override def validate(request: ProductRequest): Validated[NonEmptyChain[String], Product] = {
    validator
      .validate(request.region, request.size)
      .map { size =>
        Product(generator.newId, request.category, request.brand, request.colour, size, request.description)
      }
  }

  override def validate(request: ProductRequest, id: UUID): Validated[NonEmptyChain[String], Product] = {
    validator
      .validate(request.region, request.size)
      .map { size =>
        Product(id, request.category, request.brand, request.colour, size, request.description)
      }
  }
}

object ProductRequestValidator {

  def apply(): ProductRequestValidator = new ProductRequestValidator(SizeValidator(), UUIDGenerator())
}
