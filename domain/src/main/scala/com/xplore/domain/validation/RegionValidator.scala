package com.xplore.domain.validation

import cats.data.{NonEmptyChain, Validated}
import com.xplore.domain.{Region, Regions}

class RegionValidator {

  def validate(rawRegion: Region): Validated[NonEmptyChain[String], Region] = {
    Regions
      .all
      .find(_ == rawRegion)
      .toRight(NonEmptyChain.one(s"$rawRegion is not a valid region"))
      .fold(Validated.invalid, Validated.valid)
  }
}

object RegionValidator {

  def apply() = new RegionValidator()
}
