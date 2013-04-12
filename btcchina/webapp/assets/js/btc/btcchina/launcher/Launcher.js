// Copyright 2013 Quasars. All rights reserved.

/**
 * The main and only entrypoint for the whole web.
 *
 * It will init eventhub & try to start all registered launchers.
 * All the launcher should extends quasars.corp.launcher.BaseLauncher and
 * override init method. When init has been called, each launcher
 * should first check wether itself should launch or not.
 *
 * @author chenwenfeng1988@gmail.com (Wenfeng Chen)
 */

goog.provide('btc.btcchina.launcher.Launcher');

goog.require('goog.array');
goog.require('btc.btcchina.trade.TradeModule');


/**
 * Entry point.
 *
 * @constructor
 * @param {Object} data init json data.
 */
btc.btcchina.launcher.Launcher.init = function(data) {
  /**
   * Keep modules in alphabeta order.
   * @type {Array.<quasars.corp.launcher.BaseLauncher>}
   */
  var launchers = [
    new btc.btcchina.trade.TradeModule()
  ];
  goog.array.forEach(launchers, function(launcher) {
    if (!COMPILED) {
      launcher.init(data);
    } else {
      try {
        launcher.init(data);
      } catch (e) {
        // ignore
      }
    }
  });
};


goog.exportSymbol('btc.btcchina.launcher.Launcher.init',
    btc.btcchina.launcher.Launcher.init);
