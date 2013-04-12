/**
 * Copyright 2013 Quasars.  All Rights Reserved.
 *
 * Url helper.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */

goog.provide('btc.btcchina.util.UrlHelper');

goog.require('goog.Uri');
goog.require('goog.Uri.QueryData');
goog.require('goog.dom');
goog.require('goog.string.format');
goog.require('quasars.corp.util.BrowserHelper');


/**
 * Util method to build other url.
 *
 * @param {string} pathFormat The pattern used to build url.
 * @param {Object=} opt_params Parameters.
 * @param {?string=} opt_fragment Option fragment.
 * @param {...string|number} var_args Values to fill pathFormat.
 * @return {string} return url.
 */
btc.btcchina.util.UrlHelper.accountsServerFullUrl = function(pathFormat,
    opt_params, opt_fragment, var_args) {
  var args = Array.prototype.slice.call(arguments);
  goog.array.splice(args, 1, 2);
  var params = opt_params || {};
  var path = goog.string.format.apply(null, args);
  var uri = goog.Uri.parse(document.location.href);
  uri.setPath(window['fullurl'] + path);
  var queryData = goog.Uri.QueryData.createFromMap(params);
  uri.setQueryData(queryData);

  if (goog.isDefAndNotNull(opt_fragment) && (opt_fragment != '')) {
    uri.setFragment(opt_fragment);
  } else {
    uri.setFragment('');
  }
  return uri.toString();
};


/**
 * Websocket Url.
 * @param {string=} opt_path the path of connect.
 * @return {string} return url.
 */
btc.btcchina.util.UrlHelper.websocketUrl = function(opt_path) {
  var url = goog.Uri.parse(document.location.href);
  return goog.Uri.resolve(url, '/socket/' + (opt_path || '')).setScheme('ws').toString();
};
