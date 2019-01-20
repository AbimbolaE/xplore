package com.xplore.domain.validation

import com.xplore.domain.Region
import com.xplore.domain.Region._

class RegionValidator {

  private val knownRegions = Seq(UK, US, EU)

  def validate(rawRegion: Region): Either[String, Region] = knownRegions.find(_ == rawRegion).toRight(s"$rawRegion is not a valid region")
}
