package com.xplore.application.config

import com.xplore.database.DatabaseConfig
import com.xplore.server.ServerConfig

case class ApplicationConfig(server: ServerConfig, database: DatabaseConfig)
