package com.xplore.domain.validation

import com.xplore.domain.Size

import scala.util.Try

class SizeValidator(regionValidator: RegionValidator) {

  private val validSizeRange: Seq[Int] = 4 to 50

  def validate(rawRegion: String, rawSize: String): Either[String, Size] = {
    val errorOrSize = Try { rawSize.toInt }
      .filter(validSizeRange.contains)
      .filter(_ % 0.5 == 0)
      .toEither
      .left
      .map(_ => s"$rawSize is not a valid size")

    val errorOrRegion = regionValidator.validate(rawRegion)

    for {
      region <- errorOrRegion
      size <- errorOrSize
    } yield Size(region, size)
  }
}
