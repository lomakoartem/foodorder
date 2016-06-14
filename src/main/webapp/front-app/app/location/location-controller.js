var module = angular.module('LocationControllers', []).controller('LocationController', ['$rootScope','$scope','$routeParams', 'AbstractService',
    function ($rootScope,$scope,$routeParams, AbstractService) {
	
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};
        $scope.trigered = false;
        $scope.regex = /\S/;
        $scope.regexNumber =  /^(0?\d|[1-4]\d|50)$/


        $scope.$watch(function(){
        }, function(newValue){
        console.log($routeParams.current);
                console.log($rootScope.view_tab);
                console.log(newValue);
                self.fetchEverything();
        });

        self.fetchEverything = function () {
            AbstractService.fetchAll('/api/locations').then(function (response) {
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
            AbstractService.addData('/api/locations', toPass).then(function (response) {
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
        
        $scope.changeTrigered = function() {
        	$scope.trigered = !$scope.trigered;
        };

        $scope.editObject = function (key) {
            AbstractService.updateData('/api/locations' + '/:documentId', $scope.editingObject).then(function (response) {
            console.log('element');
                console.log('response');
                console.log(response);
                 console.log('element-after-copy');
                $scope.dataObject.list[key] = angular.copy(response);
                editingObject={};
                $scope.editingId = null;
            }, function () {
            	$scope.changeTrigered();
            });
        };
    }]);