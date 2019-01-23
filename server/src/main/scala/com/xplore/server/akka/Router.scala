package com.xplore.server.akka

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.xplore.server.akka.product.ProductRoutes
import com.xplore.server.akka.web.WebRoutes

class Router(webRoutes: WebRoutes, productRoutes: ProductRoutes) {

  val routes: Route = webRoutes.route ~ productRoutes.route
}
