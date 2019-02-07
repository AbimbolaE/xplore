package com.xplore.server.akkahttp

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.xplore.database.{RecordConverter, Repository}
import com.xplore.database.mongo.product.ProductRecord
import com.xplore.domain.Product
import com.xplore.server.akkahttp.product.ProductRoutes
import com.xplore.server.akkahttp.web.WebRoutes

import scala.concurrent.Future

class Router(webRoutes: WebRoutes, productRoutes: ProductRoutes) {

  val routes: Route = webRoutes.route ~ productRoutes.route
}

object Router {

  def apply(converter: RecordConverter[Product, ProductRecord], repository: Repository[Future, ProductRecord]) =
    new Router(WebRoutes(), ProductRoutes(converter, repository))
}
