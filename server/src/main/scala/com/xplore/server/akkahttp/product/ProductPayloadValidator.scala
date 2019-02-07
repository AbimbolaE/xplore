package com.xplore.server.akkahttp.product

import java.util.UUID

import akka.http.scaladsl.server.Directives.{provide, reject}
import akka.http.scaladsl.server.{Directive1, ValidationRejection}
import com.xplore.domain.Product
import com.xplore.domain.validation.SizeValidator
import com.xplore.server.akkahttp.PayloadValidator
import com.xplore.server.payload.ProductPayload

class ProductPayloadValidator(sizeValidator: SizeValidator) extends PayloadValidator[ProductPayload, Product] {

  override def validate(payload: ProductPayload): Directive1[Product] = {
    sizeValidator
      .validate(payload.region, payload.size)
      .map { size => Product(UUID.randomUUID(), payload.category, payload.brand, payload.colour, size, payload.description) }
      .fold(error => reject(ValidationRejection(error)), product => provide(product))
  }
}

object ProductPayloadValidator {

  def apply() = new ProductPayloadValidator(SizeValidator())
}