angular.module('VendorControllers', ['angularjs-dropdown-multiselect']).controller('VendorController', ['$rootScope','$scope','$routeParams', 'VendorService', 'AbstractService',
    function ($rootScope,$scope,$routeParams, VendorService, AbstractService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};
        
        
        $scope.example14model = [];
        $scope.example14settings = {
            scrollableHeight: '200px',
            scrollable: true,
            closeOnBlur: true,
            smartButtonMaxItems: 3,
            smartButtonTextConverter: function(itemText, originalItem) {
                return itemText;
            }
            
            
        };
        $scope.example14data = [];
        $scope.example2settings = {
            displayProp: 'label'
        };
        

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
            $scope.loadLocations();
        };

        $scope.addToList = function (value) {
        	   	
        	console.log($scope.newObject);
        	
         	$scope.newObject.locations = { locationsId: []};
         	
         	
        	for(item in $scope.example14model){
        		$scope.newObject.locations.locationsId.push($scope.example14model[item].id);
        	}
        	
            var toPass = (angular.isDefined(value)) ? value : $scope.newObject;
            VendorService.addData(toPass).then(function (response) {
                $scope.dataObject.list.push(response);
                $scope.newObject = {active:"true"};
            }, function () {
            	$scope.clear();
            });
            //$scope.addObjectInProcess = false;
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.addObjectInProcess = false;
            $scope.newObject = {active:"true"};
            $scope.example14model = [];
        };

        $scope.loadLocations = function (){
        	
        	 $scope.example14data = [];
        	
        	AbstractService.fetchAll('list').then(function (response) {
        		for(var item in response){
        			if(response[item].active){
        			$scope.example14data.push({id:response[item].id, label: response[item].name + ' Fl. ' + response[item].floor});
        			}
        		}
        	}, function (errResponse) {
        		console.error('Error while fetching locations');
        	});
        	
        }
        
        $scope.editing = function (object) {
        	
        	$scope.loadLocations();
            
        	$scope.addObjectInProcess = false;
        	$scope.clear();
            $scope.editingObject=angular.copy(object);
            
            for(var item in $scope.editingObject.locations.locationsId){
            	$scope.example14model.push({"id":$scope.editingObject.locations.locationsId[item]});
            }            
            
            $scope.editingObject.active = $scope.editingObject.active + "";
            $scope.editingId = object.id;
        };
        
        $scope.cancel = function (object) {
            $scope.editingId = null;
            $scope.example14model = [];
        };

        $scope.editObject = function (key) {
        //	console.log($scope.editingObject);
      //  	$scope.editingObject.locations.locationsId =  $scope.example14model;
        	$scope.editingObject.locations.locationsId = [];
        	for(item in $scope.example14model){
        		$scope.editingObject.locations.locationsId.push($scope.example14model[item].id);
        	}
        	//console.log($scope.example14model);
            VendorService.updateData($scope.editingObject).then(function (response) {
            console.log('element');
                console.log('response');
                console.log(response);
                 console.log('element-after-copy');
                $scope.dataObject.list[key] = angular.copy(response);
                editingObject={};
                $scope.editingId = null;
                $scope.example14model = [];
            }, function () {
            });
        };
    }]);