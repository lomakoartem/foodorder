'use strict';

// Declare app level module which depends on views, and components
var FoodOrder = angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    'LocationControllers',
    'VendorControllers',
    'AbstractServices',
])

FoodOrder.config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {

    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider.when('/list/locations', {
        templateUrl: 'front-app/app/location/location-list.html',
        controller: 'LocationController',
        controllerAs: 'aCtrl'
    }).when('/login', {
        templateUrl: 'front-app/app/login/login-page.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginCtrl'
    }).when('/list/vendors', {
    	templateUrl: 'front-app/app/vendor/vendor-list.html',
    	controller: 'VendorController',
    	controllerAs: 'vCtrl'
    }).otherwise({
        redirectTo: '/login'
    });

}])

FoodOrder.run(['$rootScope','$location',function($rootScope,$location){
    $rootScope.loggedIn=true;
    $rootScope.userNameOut ='';
    $rootScope.$on('$locationChangeStart', function(event, next, current){
        if (($location.path() == '/list/vendors')){
        	$rootScope.changeTab('vendors');
        }
        if (($location.path() == '/list/locations')){
        	$rootScope.changeTab('locations');
        }
        
        if (($location.path() != '/login')&&(!$rootScope.loggedIn)){
            $location.path('/login');
        }
        if (($location.path() == '/login')&&($rootScope.loggedIn)){
            $location.path('/list/locations');
        }

    });

}])

FoodOrder.controller('BodyCtrl', ['$scope', '$rootScope','$location', function ($scope, $rootScope,$location) {

    $rootScope.view_tab = 'locations';
    $rootScope.changeTab = function (tab) {
        $rootScope.view_tab = tab;
        console.log(tab);
        console.log($rootScope.view_tab);
    }

    $rootScope.logOut = function(){
        $location.path('/logout');
     $rootScope.loggedIn=false;
    }
}]);
