package com.xplore.application.config

import com.typesafe.config.ConfigFactory
import com.xplore.application.config.SyncConfigLoader.ConfigException
import com.xplore.application.environment.EnvironmentDetection
import pureconfig.error.ConfigReaderFailures

import scala.util.Try
import pureconfig.generic.auto._

class SyncConfigLoader extends ConfigLoader[Try] with EnvironmentDetection {

  override def load(): Try[ApplicationConfig] = {
    val localConfig = ConfigFactory.load(s"local.conf")
    val environmentConfig = ConfigFactory.load(s"${env.name}.conf")

    val config = environmentConfig.withFallback(localConfig)

    pureconfig
      .loadConfig[ApplicationConfig](config)
      .left
      .map(ConfigException)
      .toTry
  }
}

object SyncConfigLoader {

  case class ConfigException(failures: ConfigReaderFailures) extends RuntimeException(failures.toString)
}