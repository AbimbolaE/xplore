package com.xplore.server.entity

trait ResponseFactory[E, Res] {

  def create(entity: E): Res
}
