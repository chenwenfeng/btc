// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina

import quasars.webframework.helper._

import scala.collection.mutable.ListBuffer
import unfiltered.netty._
import unfiltered.request.AgentIs



/**
 * Helper.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */
class Helper (isDevMode: Boolean, isProdMode: Boolean)
  extends PageHelperBase(isDevMode, isProdMode) {

  def isIe(req: RequestBinding): Boolean = {
    if (req != null) {
      req match {
        case AgentIs.IE(_) => true
        case _ => false
      }
    } else {
      false
    }
  }

  protected override def cssMap = CssMap

  def assetsUrl(path: String, params: Any*) =
    BtcchinaServerSetting.staticServerUrl() + "/assets/" + path.format(params: _*)
  def domainAssetsUrl(path: String, params: Any*) =
    BtcchinaServerSetting.domainStaticPath() + "assets/" + path.format(params: _*)

  def btcchinaServerFullUrl(path: String, params: Any*) =
    BtcchinaServerSetting.serverFullUrl() + "/" + path.format(params: _*)

  def serverFullUrl(path: String, params: Any*) =
    BtcchinaServerSetting.serverFullUrl() + "" + path.format(params: _*)


}
