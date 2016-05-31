'use strict';

// Declare app level module which depends on views, and components
angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    'AbstractControllers',
    'AbstractServices'
]).config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {

    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider.when('/list/:current', {
        templateUrl: 'front-app/app/food/abstract-list.html',
        controller: 'AbstractController',
        controllerAs: 'aCtrl'
    }).otherwise({
        redirectTo: '/list/:current'
    });
}]).controller('BodyCtrl', ['$scope', '$rootScope', function ($scope, $rootScope) {
    $scope.view_tab = 'locations';
    $scope.changeTab = function (tab) {
        $scope.view_tab = tab;
        console.log(tab);
    }
}]);
