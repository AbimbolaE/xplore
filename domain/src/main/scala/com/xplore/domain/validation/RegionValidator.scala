package com.xplore.domain.validation

import com.xplore.domain.Region
import com.xplore.domain.Regions

class RegionValidator {

  def validate(rawRegion: Region): Either[String, Region] = Regions.all.find(_ == rawRegion).toRight(s"$rawRegion is not a valid region")
}

object RegionValidator {

  def apply() = new RegionValidator()
}
