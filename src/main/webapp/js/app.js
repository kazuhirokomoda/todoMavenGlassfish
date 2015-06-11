'use strict';

/* easy example
// -------------------------
// app.js
// -------------------------
angular.module('App', [
    'App.services',
    'App.controllers'
]);
*/


// -------------------------
// app.js
// -------------------------
angular.module('App', [
    'App.services',
    'App.controllers',
    'App.directives'
]);
//.config(function ($routeProvider, $httpProvider) {
    // CORS...
    // http://stackoverflow.com/questions/17289195/angularjs-post-data-to-external-rest-api
    //$httpProvider.defaults.useXDomain = true;
    //delete $httpProvider.defaults.headers.common['X-Requested-With'];
//});
