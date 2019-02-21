package com.xplore.domain.validation

import cats.data.{NonEmptyChain, Validated}
import cats.syntax.apply._
import com.xplore.domain.validation.SizeValidator.Result
import com.xplore.domain.{Region, Size}

import scala.util.Try

class SizeValidator(validator: RegionValidator) {

  val isWithinRange: Double ⇒ Boolean = size ⇒ (4 to 50).contains(size.toInt)
  val isInStep: Double ⇒ Boolean = size ⇒ size % 0.5 == 0

  def validate(rawRegion: String, rawSize: String): Validated[NonEmptyChain[String], Size] = {
    val validatedRegion: Result[Region] = validator.validate(rawRegion)

    val validatedSize: Result[Double] = Try { rawSize.toDouble }
      .filter(isInStep)
      .filter(isWithinRange)
      .toEither
      .left
      .map(_ ⇒ NonEmptyChain.one(s"$rawSize is not a valid size"))
      .fold(Validated.invalid, Validated.valid)

    (validatedRegion, validatedSize).mapN(Size)
  }
}

object SizeValidator {

  type Result[A] = Validated[NonEmptyChain[String], A]

  def apply() = new SizeValidator(RegionValidator())
}