package com.xplore.server.akkahttp.validation

import akka.http.scaladsl.server.Directives.{provide, reject}
import akka.http.scaladsl.server.{Directive1, ValidationRejection}
import com.xplore.database.entity.Record.ID
import com.xplore.server.entity.RequestValidator

object RequestValidation {

  def validateNew[Req, E](validator: RequestValidator[Req, E]): Req ⇒ Directive1[E] = {
    request ⇒
      validator
        .validate(request)
        .leftMap(_.iterator)
        .leftMap(_.toSeq)
        .leftMap(_.map(ValidationRejection(_)))
        .fold(errors ⇒ reject(errors: _*), provide)
  }

  def validateExisting[Req, E](validator: RequestValidator[Req, E]): (Req, ID) ⇒ Directive1[E] = {
    (request, id) ⇒
      validator
        .validate(request, id)
        .leftMap(_.iterator)
        .leftMap(_.toSeq)
        .leftMap(_.map(ValidationRejection(_)))
        .fold(errors ⇒ reject(errors: _*), provide)
  }
}
