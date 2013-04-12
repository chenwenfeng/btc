// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.websocket

import btc.btcchina.controllers._

import quasars.app.BaseApp
import quasars.webframework.websocket._

import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import akka.actor._
import scala.collection.mutable
import scala.concurrent.duration._
import unfiltered.netty.websockets._


class WebsocketManagerActor extends BaseWebsocketManagerActor {

  case class AuthSuccess(data: String, s: WebSocket)
  override protected def authSuccess(s: WebSocket, username: String, eventId: String) {
    val messageStr = """{"type":"authSuccess"}"""
    sendToClient(s, messageStr, eventId)
  }

  override protected def onJsonMessage(s: WebSocket, msg: String, eventId: String) {
    try {
    } catch {
      case e: Throwable =>
        println("error")
        println(e)
        // bad request, drop this client
        self ! RemoveClient(s, eventId)
    }
  }

  override protected def onServerMessage(c: Any) {
    c match {
      case Value(data, optS, eventId) =>
        optS match {
          case Some(s) => sendToClient(s, data, eventId)
          case None => sendToAll(data, eventId)
        }
    }
  }

  private def sendToAll(msg: String, eventId: String) {
    try {
      sockets(eventId).values foreach { v => send(v._1, msg)}
    } catch {
      case _ : Throwable =>
        // s is not online.
    }
  }

  private def sendToClient(s: WebSocket, msg: String, eventId: String) {
    try {
      send(s, msg, eventId)
    } catch {
      case _ : Throwable =>
        // s is not online.
    }
  }
}

object WebsocketManagerActor extends
  ObjectBaseWebsocketManagerActor[WebsocketManagerActor] {

  override protected def serverName = 'Btcchina
  override protected def managerActorProvider: WebsocketManagerActor = new WebsocketManagerActor

  override def init {
    super.init
    initClearExpiredTimer
  }

  def initClearExpiredTimer {
    import system.dispatcher
    val cancellable =
      system.scheduler.schedule(0 seconds,
        1 hours,
        websocketManagerActor,
        ClearExpiredSocket)
  }
}
