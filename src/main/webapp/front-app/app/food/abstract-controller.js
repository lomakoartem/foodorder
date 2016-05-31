/**
 * Created by lomak on 29.05.2016.
 */
angular.module('AbstractControllers', []).controller('AbstractController', ['$scope', 'AbstractService',
    function ($scope, AbstractService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {};
        $scope.dataObject = {};
        self.fetchEverything = function () {
            AbstractService.fetchAll('list').then(function (response) {
                $scope.dataObject.list = response;
            }, function (errResponse) {
                console.error('Error while fetching food');
            });
        };
        self.fetchEverything();
        $scope.addObjectInProcess = false;
        $scope.addingObject = function () {
            $scope.addObjectInProcess = true;
        };

        $scope.addToList = function (value) {
            var toPass = (angular.isDefined(value)) ? value : $scope.newObject;
            AbstractService.addData(toPass).then(function (response) {
                $scope.dataObject.list.push(response);
                $scope.newObject = {};
            }, function () {

            });
            $scope.addObjectInProcess = false;
        };

        $scope.clear = function () {
            $scope.addObjectInProcess = false;
        };

        $scope.editing = function (object) {
            $scope.editingId = object.id;
        };

        $scope.editObject = function (element) {
            AbstractService.updateData(element).then(function (response) {
                element = response;
                $scope.editingId = null;
            }, function () {
            });

        };
    }]);