function ElkronApiPlugin() {}

ElkronApiPlugin.prototype.doRequest = function (options, successCallback, errorCallback) {
  cordova.exec (successCallback, errorCallback, 'ElkronApiPlugin', 'doRequest', [ options ]);
}

ElkronApiPlugin.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
  window.plugins.elkronApiPlugin = new ElkronApiPlugin();
  return window.plugins.elkronApiPlugin;
};

cordova.addConstructor(ElkronApiPlugin.install);

