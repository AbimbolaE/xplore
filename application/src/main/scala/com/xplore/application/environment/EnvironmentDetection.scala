package com.xplore.application.environment

import com.xplore.application.environment.Environment.{Local, Production}
import org.slf4j.{Logger, LoggerFactory}

trait EnvironmentDetection {

  val log: Logger = LoggerFactory.getLogger(getClass)

  private val APP_ENV = "APP_ENV"
  private val supportedEnvironments = Local :: Production :: Nil

  def env: Environment = {
    val environment = sys
      .env
      .get(APP_ENV)
      .flatMap { environmentName ⇒
        supportedEnvironments.find(_.name == environmentName)
      }
      .getOrElse(Local)

    log.info(s"Running in $environment environment")

    environment
  }
}
