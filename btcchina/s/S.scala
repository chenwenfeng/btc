// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.s

import quasars.webframework.unfiltered.netty.async.RequestBindingWithS
import quasars.webframework.unfiltered.netty.async.Router
import quasars.webframework.unfiltered.netty.async.WithSBase

import unfiltered.netty._


class S(m: ReceivedMessage) {
  var username: String = _
  var cookiesExceedTime: Long = _
  var authed = false
}

object S {

  implicit def provideRequestBindingBuilder = new Router.RequestBindingBuilder {
    override def apply(m: ReceivedMessage): RequestBindingWithS[S] =
      new RequestBindingWithS[S](m) {
        override def s = sObject
        private val sObject = new S(m)
      }
  }
}

object WithS extends WithSBase[S]
