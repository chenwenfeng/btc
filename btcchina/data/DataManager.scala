// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.data

import quasars.app.data.mongo.MongoDbSupport
import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.conversions.scala._

/**
 * DataManager.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */
object DataManager extends MongoDbSupport {

  private def withTradeColl[T](collName: String)(op: MongoCollection => T): T =
    withColl("btc_btcchina", collName)(op)

  def save(): Boolean = {
    true
  }

}
