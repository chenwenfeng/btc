// Copyright 2013 Quasars. All rights reserved.

goog.provide('btc.btcchina.trade.TradeModule');

goog.require('goog.dom');
goog.require('goog.events.Event');
goog.require('goog.events.EventType');
goog.require('goog.json');
goog.require('btc.btcchina.util.UrlHelper');
goog.require('btc.btcchina.webworker.Websocket');
goog.require('btc.btcchina.event.Event');
goog.require('btc.btcchina.event.EventType');
goog.require('quasars.corp.eventbus.EventBus');
goog.require('quasars.corp.net.AjaxRequest');
goog.require('quasars.corp.launcher.PageModule');




/**
 * Validation module.
 *
 * @constructor
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 *
 * @extends {quasars.corp.launcher.PageModule}
 */
btc.btcchina.trade.TradeModule = function() {
  goog.base(this, 'trade-page');
};
goog.inherits(btc.btcchina.trade.TradeModule, quasars.corp.launcher.PageModule);


/**
 * Init.
 * @param {Object} jsonData the page data.
 */
btc.btcchina.trade.TradeModule.prototype.initModule = function(jsonData) {
  this.container = goog.dom.getElement('trade-page');
  this.eventId = 'btcchina';
  this.bus = quasars.corp.eventbus.EventBus.getInstance();

  this.addControlListeners();
  this.addMessageListeners();
  this.websocket = btc.btcchina.webworker.Websocket.getInstance();
  this.websocket.init(this.eventId);
};


/**
 * Add Control listeners.
 *
 **/
btc.btcchina.trade.TradeModule.prototype.addControlListeners = function() {
};


/**
 * Add message listeners.
 *
 **/
btc.btcchina.trade.TradeModule.prototype.addMessageListeners = function() {
  goog.events.listen(this.bus, btc.btcchina.event.EventType.WEBSOCKET_MESSAGE,
      function(e) {
        if (e.data == '') return;
        var totalData = goog.json.parse(e.data);
        console.log(totalData);
        switch (totalData['type']) {
          case 'socketExpired':
            // NOTE(chenwenfeng): socket expired notify user refresh page and login again.
            location.reload();
            break;
          case 'xx':
            break;
          default:
        }
      }, false, this);

  goog.events.listen(this.bus, btc.btcchina.event.EventType.WS_STATUS_ONLINE,
      this.online, false, this);
};


/**
 * Online.
 *
 **/
btc.btcchina.trade.TradeModule.prototype.online = function() {
};