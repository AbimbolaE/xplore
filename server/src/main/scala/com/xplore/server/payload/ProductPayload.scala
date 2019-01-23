package com.xplore.server.payload

case class ProductPayload(brand: String, category: String, colour: String, region: String, size: String, description: Map[String, String])
