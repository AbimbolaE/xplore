package com.xplore.server.akkahttp.entity

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.xplore.domain.{Brand, Category, Colour, Region, Size}
import com.xplore.server.entity.product.{ProductRequest, ProductResponse}
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, RootJsonFormat, deserializationError}

import scala.util.Try

object JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val uuidFormat: JsonFormat[UUID] = new JsonFormat[UUID] {
    override def write(obj: UUID): JsValue = JsString(obj.toString)
    override def read(json: JsValue): UUID = {
      json match {
        case JsString(uuid) => Try { UUID.fromString(uuid) }.fold(ex => deserializationError("UUID expected", ex), identity)
        case _ => deserializationError("UUID expected")
      }
    }
  }

  implicit val categoryFormat: JsonFormat[Category] = new JsonFormat[Category] {
    override def write(obj: Category): JsValue = JsString(obj.underlying)
    override def read(json: JsValue): Category = json match {
      case JsString(category) => Category(category)
      case _ => deserializationError("Category expected")
    }
  }

  implicit val brandFormat: JsonFormat[Brand] = new JsonFormat[Brand] {
    override def write(obj: Brand): JsValue = JsString(obj.underlying)
    override def read(json: JsValue): Brand = json match {
      case JsString(brand) => Brand(brand)
      case _ => deserializationError("Brand expected")
    }
  }

  implicit val colourFormat: JsonFormat[Colour] = new JsonFormat[Colour] {
    override def write(obj: Colour): JsValue = JsString(obj.underlying)
    override def read(json: JsValue): Colour = json match {
      case JsString(colour) => Colour(colour)
      case _ => deserializationError("Colour expected")
    }
  }

  implicit val regionFormat: JsonFormat[Region] = new JsonFormat[Region] {
    override def write(obj: Region): JsValue = JsString(obj.underlying)
    override def read(json: JsValue): Region = json match {
      case JsString(region) => Region(region)
      case _ => deserializationError("Region expected")
    }
  }

  implicit val sizeFormat: RootJsonFormat[Size] = jsonFormat2(Size.apply)

  implicit val productResponseFormat: RootJsonFormat[ProductResponse] = jsonFormat7(ProductResponse.apply)

  implicit val productRequestFormat: RootJsonFormat[ProductRequest] = jsonFormat6(ProductRequest.apply)
}
