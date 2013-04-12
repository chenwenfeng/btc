/**
 * Copyright 2013 Quasars.  All Rights Reserved.
 *
 * event events
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */

goog.provide('btc.btcchina.event.Event');
goog.provide('btc.btcchina.event.EventType');

goog.require('goog.events.Event');
goog.require('goog.events.EventTarget');



/**
 * Object representing a event.
 *
 * @param {string} type Event type.
 * @param {Object|string=} opt_data event data.
 *
 * @extends {goog.events.Event}
 * @constructor
 */
btc.btcchina.event.Event = function(type, opt_data) {
  goog.events.Event.call(this, type);
  this.data = opt_data;
};
goog.inherits(btc.btcchina.event.Event, goog.events.Event);


/**
 * Event type.
 */
btc.btcchina.event.EventType = {
  ADD: 'add',
  MOVE: 'move',
  TRANSITION_MOVE: 'transition_move',
  REMOVE: 'remove',
  UPDATE: 'update',

  // websocket message
  WEBSOCKET_MESSAGE: 'websocket_message',

  // websocket status
  WS_STATUS_CHANGE: 'ws_status_change',
  WS_STATUS_OFFLINE: 'ws_status_offline',
  WS_STATUS_ERROR: 'ws_status_error',
  WS_STATUS_ONLINE: 'ws_status_online'
};
