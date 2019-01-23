package com.xplore.server.akka.web

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import org.webjars.WebJarAssetLocator

import scala.util.Try

class WebRoutes(webJarAssetLocator: WebJarAssetLocator) {

  val route: Route = {
    pathPrefix ( "webjars" ) {
      get {
        extractUnmatchedPath { path =>
          Try(webJarAssetLocator.getFullPath(path.toString))
            .fold(_ ⇒ reject, getFromResource)
        }
      }
    } ~
    pathSuffixTest(".*.(html|css|js)".r) { assetFolder =>
      getFromResourceDirectory(assetFolder)
    }
  }
}

object WebRoutes {

  def apply() = new WebRoutes(new WebJarAssetLocator)
}
