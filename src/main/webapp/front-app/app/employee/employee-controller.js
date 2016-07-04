var module = angular.module('EmployeeControllers', []).controller('EmployeeController', ['$rootScope', '$scope', '$routeParams', '$location',  'AbstractService',
    function($rootScope, $scope, $routeParams, $location, AbstractService) {

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
//        	if(pageNumber >= 1 && pageNumber <= 5){
//                $scope.controlPageSize = 4 + pageNumber;
//        	} else if(pageNumber >= 6 && (($scope.totalPages - pageNumber) < 5)) {
//    			$scope.controlPageSize = 5 + ($scope.totalPages - pageNumber); 
//        	} else {
//        		$scope.controlPageSize = 9;
//        	}
        	console.log($scope.usersPerPage);
        	if (!$scope.searchFlag) {
        		fetchData('/api/employees/pages/' + pageNumber + '?size=' + $scope.usersPerPage);
        	} else {
        		fetchData('/api/employees/search/' + $scope.searchTerm + '?pageNumber=' + pageNumber +'&size=' + $scope.usersPerPage);
        	}
        	$scope.pagination.current = pageNumber;
        	$location.search("page", pageNumber);
        	$location.search("all", null);
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
                            
            }, function() {
                console.error('Error while fetching employees');
            });
        }
        
        
        $scope.findEmployees = function (){
        	
        	if ($scope.searchTerm !== undefined && ($scope.searchTerm != null || $scope.searchTerm != '') && $scope.searchTerm.length >= 3){
        		$scope.searchFlag = true;
        		$location.search("page", 1);
        		$location.search("search", $scope.searchTerm);
        	}else{
        		if($scope.searchFlag){
        			
                	$location.search("search", null);
        			$scope.searchFlag = false;
        			$scope.searchIsEmpty.empty = true;
        			
        		}
        	}
        	
        	self.getCorrectView(1);
        }
        
        
        self.getCorrectView = function(page){
        	if($scope.checkboxShowAll.value && $scope.searchFlag == false){
        		$scope.dataObject = {};
        		self.fetchEverything();
        	}else if($scope.checkboxShowAll.value == false && $scope.searchFlag == false){
        		$scope.dataObject = {};
            	getResultsPage(page);
        	}else if($scope.checkboxShowAll.value && $scope.searchFlag){
        		$scope.dataObject = {};
        		self.fetchFound($scope.searchTerm);
        	}else if($scope.checkboxShowAll.value == false && $scope.searchFlag){
        		$scope.dataObject = {};
            	getResultsPage(page);
        	}		
        }
        
        self.fetchEverything = function () {
        	
        	$location.search("page", null);
        	$location.search("search", null);
        	$location.search("all", true);
        	
            AbstractService.fetchAll('/api/employees').then(function (response) {
            	$scope.dataObject.list = response;
            }, function (errResponse) {
            	console.error('Error while fetching employees');
            });
        };
        

        
        self.fetchFound = function (searchTerm) {
        	
        	$location.search("page", null);
        	$location.search("search", searchTerm);
        	$location.search("all", true);
        	
        	
            AbstractService.fetchAll('/api/employees/searchAll/' + searchTerm).then(function (response) {
            	$scope.dataObject.list = response;
            	
                if($scope.dataObject.list.length == 0){
        			$scope.searchIsEmpty.empty = false;
        		}else{
        			$scope.searchIsEmpty.empty = true;
        		}
            	
            }, function (errResponse) {
            	console.error('Error while fetching employees');
            });
        };
        
        
        $scope.clickCheckboxShowAll = function() {	
        	self.getCorrectView(1);
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
        	$rootScope.changeTab('employees');
        	
        	if($location.search().search !== undefined){
        		$scope.searchTerm = $location.search().search;
        		$scope.searchFlag = true;
        	}
        	
        	if($location.search().all !== undefined){
                $scope.checkboxShowAll.value = $location.search().all;
        	}
        	
        	if($location.search().page !== undefined){
            	self.getCorrectView($location.search().page);
        	}else{
            	self.getCorrectView(1);
        	}
        	
//            if($location.search().page === undefined){
//            	getResultsPage(1);
//            }else if($location.search().search === undefined){
//            	getResultsPage($location.search().page);
//            }else{
//            	$scope.searchFlag = true;
//            	$scope.searchTerm = $location.search().search;
//            	getResultsPage($location.search().page);
//            }
           // getResultsPage($routeParams.page);

            //console.log($rootScope.view_tab);
            //console.log(newValue);
            //self.fetchEverything();
        });

    }]);