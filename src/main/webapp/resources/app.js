'use strict';

import angular from 'angular';
import 'angular-route';
import 'angular-resource';
import mainConfig from './config/js/main.config';
import mainController from './config/js/main.controller';
import components from './components/components.module';

// Declare app level module which depends on views, and components
var FoodOrder = angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    'AbstractServices',
    components
])
                       .config(mainConfig)
                       .controller('mainController', mainController);

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
