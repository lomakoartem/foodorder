var FoodOrder = angular.module('FoodOrder').controller('LoginCtrl', ['$scope','$rootScope','$location', function($scope,$rootScope,$location){
    $rootScope.userNameOut ='';
    $scope.user={};
    $scope.login = function(){
        $rootScope.loggedIn = true;
        $location.path('/list/locations');
        $rootScope.userNameOut = $scope.user.name;
        $rootScope.view_tab = 'locations';
    }
}]);