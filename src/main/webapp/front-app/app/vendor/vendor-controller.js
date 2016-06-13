angular.module('VendorControllers', ['angularjs-dropdown-multiselect']).controller('VendorController', ['$rootScope','$scope','$routeParams','AbstractService',
    function ($rootScope,$scope,$routeParams, AbstractService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};
        
        
        $scope.dropDownModel = [];
        $scope.dropDownSettings = {
            scrollableHeight: '200px',
            scrollable: true,
            closeOnBlur: true,
            smartButtonMaxItems: 1,
            smartButtonTextConverter: function(itemText, originalItem) {
                return itemText;
            }    
        };
        
        $scope.dropDownData = [];
//        $scope.example2settings = {
//            displayProp: 'label'
//        };
        

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
            AbstractService.fetchAll('/api/vendors').then(function (response) {
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
         	$scope.newObject.locations = { locationsId: []};         	
        	for(item in $scope.dropDownModel){
        		$scope.newObject.locations.locationsId.push($scope.dropDownModel[item].id);
        	}
        	
            var toPass = (angular.isDefined(value)) ? value : $scope.newObject;
            AbstractService.addData('/api/vendors', toPass).then(function (response) {
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
            $scope.dropDownModel = [];
        };

        $scope.loadLocations = function (){
        	 $scope.dropDownData = [];
        	AbstractService.fetchAll('/api/locations').then(function (response) {
        		for(var item in response){
        			if(response[item].active){
        			$scope.dropDownData.push({id:response[item].id, label: response[item].name + ' Fl. ' + response[item].floor});
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
            	$scope.dropDownModel.push({"id":$scope.editingObject.locations.locationsId[item]});
            }            
            
            $scope.editingObject.active = $scope.editingObject.active + "";
            $scope.editingId = object.id;
        };
        
        $scope.cancel = function (object) {
            $scope.editingId = null;
            $scope.dropDownModel = [];
        };

        $scope.editObject = function (key) {

        	$scope.editingObject.locations.locationsId = [];
        	for(item in $scope.dropDownModel){
        		$scope.editingObject.locations.locationsId.push($scope.dropDownModel[item].id);
        	}
            AbstractService.updateData('/api/vendors' + '/:documentId', $scope.editingObject).then(function (response) {
            console.log('element');
                console.log('response');
                console.log(response);
                 console.log('element-after-copy');
                $scope.dataObject.list[key] = angular.copy(response);
                editingObject={};
                $scope.editingId = null;
                $scope.dropDownModel = [];
            }, function () {
            });
        };
        
    }]);