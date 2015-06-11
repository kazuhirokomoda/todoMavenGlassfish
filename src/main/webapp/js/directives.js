'use strict';

// -------------------------
// directives.js
// -------------------------
var todosDirectives = angular.module('App.directives', []);

todosDirectives.directive('mySelect', [function () {
    return function (scope, $el, attrs) {
	scope.$watch(attrs.mySelect, function (val) {
            if (val) {
		$el[0].select();
            }
	});
    };
}]);
