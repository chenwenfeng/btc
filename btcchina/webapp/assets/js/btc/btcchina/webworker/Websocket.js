/**
 * Copyright 2013 Quasars.  All Rights Reserved.
 *
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */

goog.provide('btc.btcchina.webworker.Websocket');

goog.require('goog.events.EventTarget');
goog.require('goog.events.EventType');
goog.require('goog.net.cookies');
goog.require('btc.btcchina.event.EventType');
goog.require('btc.btcchina.util.UrlHelper');
goog.require('quasars.corp.eventbus.EventBus');
goog.require('quasars.corp.net.WebSocket');




/**
 * Fetcher.
 *
 * @constructor
 *
 * @extends {goog.events.EventTarget}
 */
btc.btcchina.webworker.Websocket = function() {
  this.init_flag = false;
};
goog.addSingletonGetter(btc.btcchina.webworker.Websocket);


/**
 * Init.
 *
 * @param {string} eventId eventid.
 */
btc.btcchina.webworker.Websocket.prototype.init = function(eventId) {
  this.eventId = eventId;
  if(this.init_flag) return;
  this.bus = quasars.corp.eventbus.EventBus.getInstance();
  var token = goog.net.cookies.get('socketToken') || '';
  var url = btc.btcchina.util.UrlHelper.websocketUrl(this.eventId);
  this.initWebSocket(url, token);

  goog.events.listen(window, goog.events.EventType.OFFLINE, function() {
    console.log('html5 offline');
    this.initWebSocket(url, token);
  }, false, this);
  this.init_flag = true;
};


/**
 * Init websocket.
 *
 * @param {string} url the websocket url.
 * @param {string} token the websocket token.
 */
btc.btcchina.webworker.Websocket.prototype.initWebSocket = function(url, token) {
  this.ws = new quasars.corp.net.WebSocket(url);
  var self = this;
  this.ws.open();
  this.ws.onOpen(function(e) {
    console.log('opened');
    self.sendMessage({
      'method': 'AUTH',
      'authToken': token
    });
  });
  this.ws.onMessage(function(e) {
    if(goog.json.parse(e.message)['type'] == 'authSuccess') {
      self.bus.dispatch(btc.btcchina.event.EventType.WS_STATUS_ONLINE, 'online');
    }
    self.bus.dispatch(btc.btcchina.event.EventType.WEBSOCKET_MESSAGE, e.message);
  });
  this.ws.onClose(function(e) {
    console.log('closed');
    self.bus.dispatch(btc.btcchina.event.EventType.WS_STATUS_OFFLINE, 'reconnecting');
  });
  this.ws.onError(function(e) {
    console.log('error');
  });
};


/**
 * Send Message to webworker.
 *
 * @param {Object|string} data the data to webworker.
 */
btc.btcchina.webworker.Websocket.prototype.sendMessage = function(data) {
  this.ws.send(data);
};
