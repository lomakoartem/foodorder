/**
 * Created by lomak on 29.05.2016.
 */
angular.module('AbstractControllers', []).controller('AbstractController', ['$scope','$routeParams', 'AbstractService',
    function ($scope,$routeParams, AbstractService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};

        $scope.$watch(function(){
         return $routeParams.current;
        }, function(newValue){
            if (angular.isDefined(newValue)&& newValue=='locations'){
                self.fetchEverything();
            }
        });

        self.fetchEverything = function () {
            AbstractService.fetchAll('list').then(function (response) {
                $scope.dataObject.list = response;
            }, function (errResponse) {
                console.error('Error while fetching food');
            });
        };

        $scope.addObjectInProcess = false;
        $scope.addingObject = function () {
        	$scope.cancel();
            $scope.addObjectInProcess = true;
        };

        $scope.addToList = function (value) {
            var toPass = (angular.isDefined(value)) ? value : $scope.newObject;
            AbstractService.addData(toPass).then(function (response) {
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
            AbstractService.updateData($scope.editingObject).then(function (response) {
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