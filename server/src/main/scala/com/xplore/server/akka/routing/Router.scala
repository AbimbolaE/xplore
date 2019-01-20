package com.xplore.server.akka.routing

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.xplore.server.akka.item.ItemRoutes

class Router(siteRoutes: SiteRoutes, itemRoutes: ItemRoutes) {

  val routes: Route = siteRoutes.route ~ itemRoutes.route
}
