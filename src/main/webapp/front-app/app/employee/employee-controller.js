var module = angular.module('EmployeeControllers', []).controller('EmployeeController', ['$rootScope', '$scope', '$routeParams', 'AbstractService',
    function($rootScope, $scope, $routeParams, AbstractService) {

        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active: "true"};
        $scope.editingObject = {};
        $scope.dataObject = {};
        $scope.trigered = false;

        $scope.style = '';
        $scope.controlPageSize = 5;

        $scope.checkboxShowAll = {
            value: false
        };

        $scope.users = [];
        $scope.totalUsers = 0;
        $scope.usersPerPage = 20; // this should match however many results
                                  // your API puts on one page

        $scope.pagination = {
            current: 1
        };

        $scope.pageChanged = function(newPage) {
            getResultsPage(newPage);
            var myDiv = document.getElementById('scrTop');
            myDiv.scrollTop = 0;
        };

        function getResultsPage(pageNumber) {
            if(pageNumber >= 1 && pageNumber <= 5) {
                $scope.controlPageSize = 4 + pageNumber;
            } else {

                if(pageNumber > 5 && pageNumber < $scope.totalPages - 4) {
                    $scope.controlPageSize = 9;
                } else {
                    $scope.controlPageSize = 4 + ($scope.totalPages - pageNumber) + 1;
                }

            }

            AbstractService.fetchPage('/api/employees/pages/' + pageNumber + '?size=' + $scope.usersPerPage).then(function(response) {
                $scope.users = response.content;
                $scope.totalUsers = response.totalElements;
            }, function() {
                console.error('Error while fetching employees');
            });
        }

        //        $scope.plusTwenty = function(){
        //        	if($scope.totalUsers > ($scope.usersPerPage + 20) ){
        //            $scope.usersPerPage += 20;
        //        	}else{
        //        		$scope.usersPerPage = $scope.totalUsers;
        //        	}
        //        }

        $scope.clickCheckboxShowAll = function() {
            if($scope.checkboxShowAll.value) {
                $scope.usersPerPage = parseInt($scope.totalUsers)
            } else {
                $scope.usersPerPage = 20;
            }
        };

        $scope.checkStyle = function(data) {

            if(!data) {
                return $scope.style;
            } else {
                return '';
            }
        };

        $scope.regex = /\S/;
        $scope.regexNumber = /^([1-9]|[1-4]\d|50)$/;

        $scope.$watch(function() {
        }, function() {
            console.log($routeParams.current);
            getResultsPage(1);

            //console.log($rootScope.view_tab);
            //console.log(newValue);
            //self.fetchEverything();
        });

        /*   self.fetchEverything = function () {
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
         $scope.style = '';
         $scope.addObjectInProcess = false;
         }, function () {
         //$scope.clear();
         $scope.changeTrigered();
         $scope.style = 'focusred';
         });
         };

         $scope.clear = function () {
         $scope.addObjectInProcess = false;
         $scope.newObject = {active:"true"};
         $scope.style = '';
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
         $scope.style = '';
         }, function () {
         $scope.style = 'focusred';
         $scope.changeTrigered();
         });
         };*/
    }]);