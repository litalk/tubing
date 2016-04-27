'use strict';
angular.module('login')
  .constant('Config', {
    CLIENT_ID: '470859838725-li029m9pk87hivd127f0fr60k4u0sm97.apps.googleusercontent.com',
    // gulp environment: injects environment vars
    ENV: {
      /*inject-env*/
      'SERVER_URL': 'https://DEVSERVER/api',
      'SOME_OTHER_URL': '/proxy'
      /*endinject*/
    },

    // gulp build-vars: injects build vars
    BUILD: {
      /*inject-build*/
      /*endinject*/
    }

  });
