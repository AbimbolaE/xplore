package com.xplore

package object domain {

  implicit class Brand(val underlying: String) extends AnyVal
  implicit class Category(val underlying: String) extends AnyVal
  implicit class Colour(val underlying: String) extends AnyVal
  implicit class Region(val underlying: String) extends AnyVal

  object Region {

    val UK: Region = Region("UK")
    val US: Region = Region("US")
    val EU: Region = Region("EU")
  }
}
