package com.xplore.application.environment

trait Environment {
  val name: String = getClass.getSimpleName.dropRight(1).toLowerCase()
}

object Environment {
  case object Local extends Environment
  case object Production extends Environment
}