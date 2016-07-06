'use strict';

import angular from 'angular';
import 'angular-route';
import 'angular-resource';
import mainController from './config/js/main.controller';
import components from './components/components.module';

// Declare app level module which depends on views, and components
var FoodOrder = angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    'AbstractServices',
    components
])
                       .controller('mainController', mainController);

FoodOrder.config(['$locationProvider', '$routeProvider', '$httpProvider', function($locationProvider, $routeProvider, $httpProvider) {

    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider.when('/list/locations', {
        templateUrl: 'resources/views/location.page.html'
    }).when('/list/vendors', {
        templateUrl: 'resources/views/vendor.page.html'
    }).when('/list/employees', {
        templateUrl: 'resources/views/employee.page.html',
        reloadOnSearch: false
    }).when('/login', {
        templateUrl: 'front-app/app/login/login-page.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginCtrl'
    }).otherwise({
        redirectTo: '/login'
    });

}]);

FoodOrder.run(['$rootScope', '$location', function($rootScope, $location) {
    $rootScope.loggedIn = true;
    $rootScope.userNameOut = '';
    $rootScope.$on('$locationChangeStart', function() {
        // if (($location.path() == '/list/vendors')){
        // 	$rootScope.changeTab('vendors');
        // }
        // if (($location.path() == '/list/locations')){
        // 	$rootScope.changeTab('locations');
        // }
        //
        // if (($location.path() == '/list/employees')){
        // 	$rootScope.changeTab('employees');
        // }

        if(($location.path() != '/login') && (!$rootScope.loggedIn)) {
            $location.path('/login');
        }
        if(($location.path() == '/login') && ($rootScope.loggedIn)) {
            $location.path('/list/locations');
        }

    });

}]);
