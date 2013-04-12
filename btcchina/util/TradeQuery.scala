// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.util

import dispatch._
import dispatch.jsoup.JSoupHttp._
import org.jsoup.nodes._
import scala.collection.JavaConversions._

object TradeQuery {
  val queryHost = :/("https://data.btcchina.com")

  private def tradeUrl = queryHost / "data/ticker"

  def getTrade() = {
    Http(tradeUrl as_str)
  }
}
