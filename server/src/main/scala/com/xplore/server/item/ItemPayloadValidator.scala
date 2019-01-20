package com.xplore.server.item

import akka.http.scaladsl.server.Directives.{provide, reject}
import akka.http.scaladsl.server.{Directive1, ValidationRejection}
import com.xplore.domain.Item
import com.xplore.domain.validation.SizeValidator
import com.xplore.server.akka.validation.PayloadValidator

class ItemPayloadValidator(sizeValidator: SizeValidator) extends PayloadValidator[ItemPayload, Item] {

  override def validate(payload: ItemPayload): Directive1[Item] = {
    sizeValidator
      .validate(payload.region, payload.size)
      .map { size => Item(payload.brand, payload.category, payload.colour, size, payload.description) }
      .fold(error => reject(ValidationRejection(error)), item => provide(item))
  }
}
