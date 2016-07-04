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
            value : false
        };

        $scope.searchFlag = false;
        $scope.searchIsEmpty = {
        		empty : true
        }
        $scope.dirControl = 0;
        $scope.users = [];
        $scope.totalUsers = 0;
        $scope.usersPerPage = 20; // this should match however many results
                                  // your API puts on one page
        $scope.totalPages = 0;
        $scope.searchTerm = '';
        $scope.pagination = {
            current: 1
        };

        $scope.pageChanged = function(newPage) {
            getResultsPage(newPage);
            var myDiv = document.getElementById('scrTop');
            myDiv.scrollTop = 0;
        };

        function getResultsPage(pageNumber) {
        	if(pageNumber >= 1 && pageNumber <= 5){
                $scope.controlPageSize = 4 + pageNumber;
        	} else if(pageNumber >= 6 && (($scope.totalPages - pageNumber) < 5)) {
    			$scope.controlPageSize = 5 + ($scope.totalPages - pageNumber); 
        	} else {
        		$scope.controlPageSize = 9;
        	}
        	if (!$scope.searchFlag) {
        		fetchData('/api/employees/pages/' + pageNumber + '?size=' + $scope.usersPerPage);
        		$scope.pagination.current = pageNumber;
        	} else {
        		fetchData('/api/employees/search/' + $scope.searchTerm + '?pageNumber=' + pageNumber +'&size=' + $scope.usersPerPage);
        		$scope.pagination.current = pageNumber;
        	}
        }


        function fetchData(requestString){
            AbstractService.fetchPage(requestString).then(function(response) {
                $scope.users = response.content;
                $scope.totalUsers = response.totalElements;
                $scope.totalPages = response.totalPages;
                
                if(response.content == 0 && $scope.searchFlag){
        			$scope.searchIsEmpty.empty = false;
        		}else{
        			$scope.searchIsEmpty.empty = true;
        		}
                
                $scope.clickCheckboxShowAll();
                
            }, function() {
                console.error('Error while fetching employees');
            });
        }
        
        
        $scope.findEmployees = function (){
        	if ($scope.searchTerm !== undefined && ($scope.searchTerm != null || $scope.searchTerm != '') && $scope.searchTerm.length >= 3){
        		$scope.searchFlag = true;
        		getResultsPage(1);
        		
        	}else{
        		if($scope.searchFlag){
        			$scope.searchFlag = false;
        			$scope.searchIsEmpty.empty = true;
            		getResultsPage(1);
        		}
        	}
        };
        
        $scope.clickCheckboxShowAll = function() {
            if($scope.checkboxShowAll.value) {
                $scope.usersPerPage = parseInt($scope.totalUsers);
            } else {
                $scope.usersPerPage = 20;
            }
        };
		
		$scope.sendFile = function(){
        	var formData = new FormData();
        	formData.append("file", file.files[0]);
        	
        	AbstractService.upload('/api/employees/upload', formData).then(function (response) {
        		getResultsPage(1);
            }, function (errResponse) {
            	console.log('Error while uploading employees from file');
            });
        }

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
        });

    }]);