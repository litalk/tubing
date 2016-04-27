'use strict';
angular.module('login')
  .controller('LoginCtrl', function ($log, $http, $timeout, LoginService, Config, $cordovaDevice, $cordovaOauth) {
    var self = this;

    // bind data from services
    //this.someData = Main.someData;
    //this.ENV = Config.ENV;
    //this.BUILD = Config.BUILD;
    // get device info
    ionic.Platform.ready(function () {
      if (ionic.Platform.isWebView()) {
        self.device = $cordovaDevice.getDevice();
      }
    }.bind(self));

    var methods = {
      getDeviceType: function () {
        $log.info(self.device, ionic.Platform.platform());

        $cordovaOauth.google(Config.CLIENT_ID, ['https://www.googleapis.com/auth/youtube']).then(function (res) {
          $log.info(res);
        }, function (err) {
          $log.info(err);
        });
      }
    };

    function init() {
      self.methods = methods;
    }

    init();
    // PASSWORD EXAMPLE
    //this.password = {
    //  input: '', // by user
    //  strength: ''
    //};
    //this.grade = function () {
    //  var size = this.password.input.length;
    //  if (size > 8) {
    //    this.password.strength = 'strong';
    //  } else if (size > 3) {
    //    this.password.strength = 'medium';
    //  } else {
    //    this.password.strength = 'weak';
    //  }
    //};
    //this.grade();
    //
    //// Proxy
    //this.proxyState = 'ready';
    //this.proxyRequestUrl = Config.ENV.SOME_OTHER_URL + '/get';
    //
    //this.proxyTest = function () {
    //  this.proxyState = '...';
    //
    //  $http.get(this.proxyRequestUrl)
    //    .then(function (response) {
    //      $log.log(response);
    //      this.proxyState = 'success (result printed to browser console)';
    //    }.bind(this))
    //    .then($timeout(function () {
    //      this.proxyState = 'ready';
    //    }.bind(this), 6000));
    //};

  });
