// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.routes

import btc.btcchina.s.WithS
import btc.btcchina.controllers._
import quasars.util.auth.AuthHelper
import quasars.util.auth.TokenAndExceedTime
import quasars.webframework.unfiltered.request.QParams._

import scala.util.control.Exception._
import scala.compat.Platform._
import scala.concurrent.duration._
import unfiltered.Cookie
import unfiltered.request._
import unfiltered.response._
import unfiltered.netty.request._

object HttpRoute {
  val auth = new Route {
    def intent = {
      case req => req match {
        case Cookies(cookies) if cookies.contains(AuthKey) =>
          val value = cookies(AuthKey).get.value
          AuthHelper.decode(value) match {
            case Some(TokenAndExceedTime(u, t)) if (t >= currentTime) =>
              s.username = u
              s.cookiesExceedTime = t
              s.authed = true
              Pass
            case _ =>
              val username = "quasars"
              val token = AuthHelper.encode(
                TokenAndExceedTime(username, currentTime + 7.days.toMillis)
              )
              req.respond(SetCookies(Cookie(AuthKey, token,
                maxAge = Some(7.days.toSeconds.toInt), path = Some("/"))) ~> Redirect("/"))
          }
        case _ =>
          val username = "quasars"
          val token = AuthHelper.encode(
            TokenAndExceedTime(username, currentTime + 7.days.toMillis)
          )
          req.respond(SetCookies(Cookie(AuthKey, token,
            maxAge = Some(7.days.toSeconds.toInt), path = Some("/"))) ~> Redirect("/"))
      }
    }
  }


  val t = new Route {
    def intent = {
      case req => req match {
        case GET(Path("/")) =>
          if(s.authed) {
            req.respond(HtmlContent ~> ResponseString(Controller.trade()))
          } else {
            val username = "quasars"
            val token = AuthHelper.encode(
              TokenAndExceedTime(username, currentTime + 7.days.toMillis)
            )
            req.respond(SetCookies(Cookie(AuthKey, token,
              maxAge = Some(7.days.toSeconds.toInt), path = Some("/"))) ~> Redirect("/"))
          }
        case _ => Pass
      }
    }
  }

}
