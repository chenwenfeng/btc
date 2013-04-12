// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina

import quasars.app.config.BaseSetting
import quasars.app.config.SettingDsl._


/**
 * Setting for btcchina server.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */
object BtcchinaServerSetting extends BaseSetting {

  val serverUrl = "web.url.btcchina.server" :: SString("") !!
    "server url"
  val serverFullUrl = "web.url.btcchina.fullserver" :: SString !!
    "server full url"
  val staticServerUrl = "web.url.static.server" :: SString("") !! "Static server url"
  val domainStaticPath = "web.url.domain.static.path" :: SString("/") !! "Domain static path"
}
