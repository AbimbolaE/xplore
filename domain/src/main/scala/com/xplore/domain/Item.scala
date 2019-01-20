package com.xplore.domain

case class Item(brand: Brand, category: Category, colour: Colour, size: Size, description: Map[String, String])
