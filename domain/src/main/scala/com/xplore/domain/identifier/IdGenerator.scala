package com.xplore.domain.identifier

trait IdGenerator[I] {

  def newId: I
}
