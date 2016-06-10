angular.module('VendorControllers', []).controller('VendorController', ['$rootScope','$scope','$routeParams', 'VendorService',
    function ($rootScope,$scope,$routeParams, VendorService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};

        $scope.$watch(function(){
//         return $routeParams.current;
        }, function(newValue){
//        console.log($routeParams.current);
                console.log($rootScope.view_tab);
                console.log(newValue);
//            if (angular.isDefined(newValue)&& newValue=='vendors'){
//                $rootScope.show_table = false;
                self.fetchEverything();
//            }
        });

        self.fetchEverything = function () {
            VendorService.fetchAll('list').then(function (response) {
                $scope.dataObject.list = response;
            }, function (errResponse) {
                console.error('Error while fetching vendors');
            });
        };

        $scope.addObjectInProcess = false;
        $scope.addingObject = function () {
        	$scope.cancel();
            $scope.addObjectInProcess = true;
        };

        $scope.addToList = function (value) {
            var toPass = (angular.isDefined(value)) ? value : $scope.newObject;
            VendorService.addData(toPass).then(function (response) {
                $scope.dataObject.list.push(response);
                $scope.newObject = {active:"true"};
            }, function () {
            	$scope.clear();
            });
            $scope.addObjectInProcess = false;
        };

        $scope.clear = function () {
            $scope.addObjectInProcess = false;
            $scope.newObject = {active:"true"};
        };

        $scope.editing = function (object) {
        	$scope.addObjectInProcess = false;
        	$scope.clear();
            $scope.editingObject=angular.copy(object);
            $scope.editingObject.active = $scope.editingObject.active + "";
            $scope.editingId = object.id;
        };
        
        $scope.cancel = function (object) {
            $scope.editingId = null;
        };

        $scope.editObject = function (key) {
            VendorService.updateData($scope.editingObject).then(function (response) {
            console.log('element');
                console.log('response');
                console.log(response);
                 console.log('element-after-copy');
                $scope.dataObject.list[key] = angular.copy(response);
                editingObject={};
                $scope.editingId = null;
            }, function () {
            });
        };
    }]);