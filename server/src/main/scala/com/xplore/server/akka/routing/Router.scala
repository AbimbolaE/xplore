package com.xplore.server.akka.routing

import akka.http.scaladsl.server.Route

class Router(itemRoutes: ItemRoutes){

  val routes: Route = itemRoutes.route
}
