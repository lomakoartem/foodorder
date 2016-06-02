'use strict';

// Declare app level module which depends on views, and components
var FoodOrder = angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    'AbstractControllers',
    'AbstractServices',
])

FoodOrder.config(['$locationProvider', '$routeProvider', '$httpProvider', function ($locationProvider, $routeProvider, $httpProvider) {

    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider.when('/list/:current', {
        templateUrl: 'front-app/app/food/abstract-list.html',
        controller: 'AbstractController',
        controllerAs: 'aCtrl'
    }).when('/login', {
        templateUrl: 'front-app/app/login/login-page.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginCtrl'
    }).otherwise({
        redirectTo: '/login'
    });

}])

FoodOrder.run(['$rootScope','$location',function($rootScope,$location){
    $rootScope.loggedIn=true;
    $rootScope.userNameOut ='';
    $rootScope.$on('$locationChangeStart', function(event, next, current){
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
    $scope.show_table = false;
    $rootScope.changeTab = function (tab) {
        $rootScope.view_tab = tab;
        if($rootScope.view_tab === 'locations'){
            $scope.show_table = false;
        }else{
            $scope.show_table = true;
        }
        console.log(tab);
    }

    $rootScope.checkTable= function(){
    	return $scope.show_table;
    }
    
    $rootScope.logOut = function(){
     $rootScope.loggedIn=false;
     $location.path('/login');
    }
}]);
