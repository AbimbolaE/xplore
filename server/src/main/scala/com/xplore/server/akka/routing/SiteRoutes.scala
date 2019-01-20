package com.xplore.server.akka.routing

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._

class SiteRoutes {

  val route: Route = pathSuffixTest("(.*).html".r | "(.*).css".r | "(.*).js".r) { _ =>
    getFromResourceDirectory("html")
  }
}

object SiteRoutes {

  def apply() = new SiteRoutes()
}
