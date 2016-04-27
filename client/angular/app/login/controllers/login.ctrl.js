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
        methods.doGoogleSignIn();
        //$log.info(self.device, ionic.Platform.platform());
        //
        //$cordovaOauth.google(Config.CLIENT_ID, ['https://www.googleapis.com/auth/youtube'], {access_type: "offline"}).then(function (res) {
        //  $log.info(res);
        //
        //  $http.post(Config.ENV.SERVER_URL + '/login', {token: res.access_token}).then(function (res) {
        //    $log.info(res);
        //  });
        //}, function (err) {
        //  $log.info(err);
        //});
      },
      doGoogleSignIn: function () {
        var auth_url = 'response_type=code&redirect_uri=' + encodeURIComponent('http://localhost/callback') + '&client_id=' + Config.CLIENT_ID + '&access_type=offline&scope=' + encodeURIComponent('https://www.googleapis.com/auth/youtube');
        var encoded_url = 'https://accounts.google.com/o/oauth2/v2/auth?' + auth_url;

        var browserRef = window.cordova.InAppBrowser.open(encoded_url, '_blank', 'location=no');

        browserRef.addEventListener("loadstart", function (event) {
          if ((event.url).indexOf('http://localhost/callback') === 0) {
            var requestToken = (event.url).split("code=")[1];
            $log.info(requestToken);

            var config = {
              method: 'POST',
              url: Config.ENV.SERVER_URL + '/login',
              data: {
                auth_code: requestToken
              },
              headers: {
                'Content-Type': 'application/json'
              }
            };

            $http(config)
              .then(function (res) {
                $log.info(res);
              }, function (err) {
                $log.info(err);
              });

          }
        });
      }
    };

    function init() {
      self.methods = methods;
    }

    init();

  });
