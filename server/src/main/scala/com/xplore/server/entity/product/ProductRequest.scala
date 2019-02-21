package com.xplore.server.entity.product

case class ProductRequest(brand: String, category: String, colour: String, region: String, size: String, description: Map[String, String])
