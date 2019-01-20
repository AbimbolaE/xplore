package com.xplore.server.akka.item

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class ItemPayload(brand: String, category: String, colour: String, region: String, size: String, description: Map[String, String])

object ItemPayload extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val format: RootJsonFormat[ItemPayload] = jsonFormat6(ItemPayload.apply)
}
