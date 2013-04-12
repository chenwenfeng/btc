// Copyright 2013 QUASARS. All rights reserved.

package btc.btcchina

import btc.btcchina.routes._
import btc.btcchina.s.S
import btc.btcchina.websocket.WebsocketManagerActor

import quasars.app.BaseApp
import quasars.app.BaseAppSetting
import quasars.app.NettyUnfilteredWebServerSupport

import akka.actor._
import akka.event.EventStream
import scala.collection.JavaConversions._
import scala.collection.mutable.ConcurrentMap
import unfiltered.netty.Http
import unfiltered.netty.websockets._
import unfiltered.request._
import unfiltered.response._
import unfiltered.util._

/**
 * Btcchina Server.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */
object BtcchinaServer extends
  BaseApp("BtcchinaServer") with NettyUnfilteredWebServerSupport {

  addSettings(
    BtcchinaServerSetting,
    quasars.util.crypto.AuthSetting,
    quasars.app.data.mongo.MongoDbSetting
  )

  init {
    WebsocketManagerActor.init
  }

  import S.provideRequestBindingBuilder
  import ErrorHandler.provider
  addNettyUnfilteredWebServer("http") { server =>
    server.plan(Planify(
      WebsocketRoute.ws
    ).onPass(
      _ sendUpstream _
    )).plan(router(
      HttpRoute.auth,
      HttpRoute.t
    ))
  }

  lazy val actorSystems = Map(
    'Btcchina -> ActorSystem("Btcchina", getConfig.getConfig("Btcchina"))
  )
  override def provideActorSystems = actorSystems
  lazy val eventStreams = Map(
    'Btcchina -> new EventStream(debug = BaseAppSetting.isDevMode)
  )
  override def provideEventStreams = eventStreams
}
