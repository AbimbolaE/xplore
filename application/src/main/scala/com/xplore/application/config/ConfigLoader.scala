package com.xplore.application.config

import scala.language.higherKinds

trait ConfigLoader[F[_]] {

  def load(): F[ApplicationConfig]
}
