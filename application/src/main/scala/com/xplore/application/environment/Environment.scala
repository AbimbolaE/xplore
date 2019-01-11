package com.xplore.application.environment

trait Environment {
  val name: String = getClass.getSimpleName.toLowerCase()
}

object Environment {
  case object Local extends Environment
  case object Prod extends Environment
}