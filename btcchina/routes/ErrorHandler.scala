// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.routes

import btc.btcchina.Helper
import quasars.app.BaseAppSetting
import quasars.webframework.unfiltered.netty.async.Router

object ErrorHandler extends Router.ErrorMessageBuilder {

  override def errorMessage: String = {
    ScalateHelper.render("errors/50x.scaml")(Map("helper" -> helper))
  }

  lazy val helper = new Helper(BaseAppSetting.isDevMode, BaseAppSetting.isProdMode)
}
