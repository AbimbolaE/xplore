package com.xplore.application.config

trait ConfigLoader[F[_]] {

  def load(): F[ApplicationConfig]
}
