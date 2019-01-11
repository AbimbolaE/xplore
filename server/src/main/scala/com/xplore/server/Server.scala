package com.xplore.server

import com.xplore.server.Server.Handle

import scala.language.higherKinds

trait Server[F[_]] {

  def run(): F[Handle[F]]
}

object Server {

  trait Handle[F[_]] {

    def shutdown(): F[_]
  }

  object Handle {

    def apply[F[_]](task: ⇒ F[_]): Handle[F] = () => task
  }
}
