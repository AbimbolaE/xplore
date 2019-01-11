package com.xplore.application.environment

import com.xplore.application.environment.Environment.{Local, Prod}

trait EnvironmentDetection {

  private val APP_ENV = "APP_ENV"
  private val supportedEnvironments = Local :: Prod :: Nil

  def env: Environment = {
    sys
      .env
      .get(APP_ENV)
      .flatMap { environmentName ⇒
        supportedEnvironments.find(_.name == environmentName)
      }
      .getOrElse(Local)
  }
}
