package com.xplore.server.akka

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.xplore.server.payload.ProductPayload
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val format: RootJsonFormat[ProductPayload] = jsonFormat6(ProductPayload.apply)
}
