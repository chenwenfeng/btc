// Copyright 2013 Quasars. All rights reserved.

package btc.btcchina.routes

import btc.btcchina.Helper
import btc.btcchina.s.S
import btc.btcchina.s.WithS
import quasars.app.BaseAppSetting
import quasars.webframework.unfiltered.ScalateSupport
import quasars.webframework.unfiltered.RouteScalate
import quasars.webframework.unfiltered.netty.async.{ Route => RouteBase }

import org.fusesource.scalate.Binding
import scala.util.DynamicVariable
import unfiltered.netty._
import unfiltered.request._
import unfiltered.response._


object ScalateHelper extends ScalateSupport {

  protected override def scalateDefaultLayoutUri: String = "layout.scaml"

  // TODO(timgreen): refactor following lines
  scalateEngine.bindings =
    Binding("import val helper: btc.btcchina.Helper") ::
    Binding("val req: unfiltered.netty.RequestBinding = null") ::
    scalateEngine.bindings

  scalateEngine.packagePrefix = "scalatetemplate.web"
}

/**
 * Route base.
 *
 * @author timgreen@i-md.com (Tim Green)
 */
abstract class Route extends RouteBase with RouteScalate {

  protected val AuthKey = "socketToken"

  implicit def s: S = {
    val WithS(s) = req
    s
  }

  override def scalateHelper = ScalateHelper

  implicit def provideBindingAtrribute: Map[String, Any] = Map(
    "helper" -> helper,
    "req" -> req
  )

  implicit lazy val helper = new Helper(BaseAppSetting.isDevMode, BaseAppSetting.isProdMode)

  val _params = new DynamicVariable[Params.Map](null)
  protected def params = _params.value

  override def beforeRun(req: RequestBinding)(op: => Unit): Unit = {
    _params.withValue(Params.unapply(req).get) {
      op
    }
  }

  def notFound(map: (String, Any)*) {
    req.respond(NotFound ~> HtmlContent ~> scalate("errors/404.scaml", map.toMap))
  }
  def badRequest(msg: String = "") {
    req.respond(BadRequest ~> PlainTextContent ~> ResponseString(msg))
  }
  def jsonBadRequest(msg: String = "") {
    req.respond(BadRequest ~> JsonContent ~> ResponseString(msg))
  }

  def json(json: String = "") {
    req.respond(JsonContent ~> ResponseString(json))
  }

  import quasars.webframework.unfiltered.request.QParams._
  def process[A](expected: QueryM[QueryResult[A]]) {
    expected(params) match {
      case Right(_) =>
        badRequest()
      case _ =>
    }
  }
  def processJson[A](expected: QueryM[QueryResult[A]]) {
    expected(params) match {
      case Right(_) =>
        jsonBadRequest()
      case _ =>
    }
  }
}
