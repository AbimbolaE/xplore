package com.xplore

import java.util.UUID

package object domain {

  implicit class Category(val underlying: String) extends AnyVal
  implicit class Brand(val underlying: String) extends AnyVal
  implicit class Colour(val underlying: String) extends AnyVal
  implicit class Region(val underlying: String) extends AnyVal

  object Regions {

    val UK: Region = new Region("UK")
    val US: Region = new Region("US")
    val EU: Region = new Region("EU")

    val all: Seq[Region] = Seq(UK, US, EU)
  }

  case class Product(id: UUID, category: Category, brand: Brand, colour: Colour, size: Size, description: Map[String, String])

  case class Size(region: Region, value: Double)
}
