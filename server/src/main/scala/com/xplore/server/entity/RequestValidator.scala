package com.xplore.server.entity

import cats.data.{NonEmptyChain, Validated}
import com.xplore.database.entity.Record.ID

trait RequestValidator[Req, E] {

  def validate(request: Req): Validated[NonEmptyChain[String], E]
  def validate(request: Req, id: ID): Validated[NonEmptyChain[String], E]
}
