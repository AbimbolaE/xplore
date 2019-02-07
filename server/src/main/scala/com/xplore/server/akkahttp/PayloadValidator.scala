package com.xplore.server.akkahttp

import akka.http.scaladsl.server.Directive1

trait PayloadValidator[I, O] {

  def validate(payload: I): Directive1[O]
}
