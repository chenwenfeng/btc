// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.controllers

import btc.btcchina.data._
import btc.btcchina.util._

import quasars.app.BaseApp
import quasars.util.thrift.ConvertHelper
import quasars.webframework.websocket.EventBusMessage

import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import unfiltered.netty.websockets._

/**
 * Controller.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */
case class Value(data: String, s: Option[WebSocket] = None, path: String) extends EventBusMessage

object Controller {

  def trade () = {
    TradeQuery.getTrade()
  }

  def sendMessage(messageType: String, data: String, path: String, s: Option[WebSocket] = None) {
    val eventBus = BaseApp.getEventStream('Socket)
    val messageStr = """{"type":"%s","data":%s}""".format(messageType, data)
    eventBus.publish(Value(messageStr, s, path))
  }
}
