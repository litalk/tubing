'use strict';
angular.module('login')
  .constant('Config', {
    //CLIENT_ID: '470859838725-li029m9pk87hivd127f0fr60k4u0sm97.apps.googleusercontent.com',
    CLIENT_ID: '470859838725-ougb4srsc9ji055e7n3ristsjul43if3.apps.googleusercontent.com',
    // gulp environment: injects environment vars
    ENV: {
      /*inject-env*/
      'SERVER_URL': 'http://BARSHEAN3.emea.hpqcorp.net:8080/tubing',
      'SOME_OTHER_URL': '/proxy'
      /*endinject*/
    },

    // gulp build-vars: injects build vars
    BUILD: {
      /*inject-build*/
      /*endinject*/
    }

  });
