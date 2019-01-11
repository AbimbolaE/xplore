package com.xplore.domain

import java.util.UUID

import com.xplore.domain.Item._

case class Item(id: UUID, brand: Brand, category: Category, colour: Colour, size: Size, description: Description)

object Item {

  case class Brand(value: String)
  case class Category(value: String)
  case class Colour(value: String)

  sealed trait Region
  object Region {
    object UK extends Region
    object US extends Region
    object EU extends Region
  }

  case class Size(region: Region, value: String)

  trait Description
  object Description {
    case class ShoeDescription(heel: String, kind: String, design: String) extends Description
  }
}
