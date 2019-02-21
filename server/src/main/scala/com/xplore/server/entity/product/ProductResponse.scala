package com.xplore.server.entity.product

import java.util.UUID

case class ProductResponse(id: UUID, brand: String, category: String, colour: String, region: String, size: Double, description: Map[String, String])
