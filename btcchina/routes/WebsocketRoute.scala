// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.routes

import btc.btcchina.websocket.WebsocketManagerActor

import quasars.webframework.websocket._

import unfiltered.netty.websockets._
import unfiltered.request._
import unfiltered.response._


object WebsocketRoute {

  import WebsocketManagerActor._

  val ws: unfiltered.netty.websockets.Plan.Intent = {
    case Path(Seg("socket" :: path :: Nil)) => {
      case Open(s) =>
        websocketManagerActor ! AddClient(s, path)
      case Message(s, Text(msg)) =>
        websocketManagerActor ! OnClientMessage(s, msg, path)
      case Close(s) => websocketManagerActor ! RemoveClient(s, path)
      case Error(s, e) =>
        e.printStackTrace
    }
  }

}
