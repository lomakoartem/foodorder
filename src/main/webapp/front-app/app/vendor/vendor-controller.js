angular.module('VendorControllers', ['angularjs-dropdown-multiselect']).controller('VendorController', ['$rootScope','$scope','$routeParams','AbstractService',
    function ($rootScope,$scope,$routeParams, AbstractService) {
        var self = this;
        $scope.editingId = null;
        $scope.newObject = {active:"true"};
        $scope.editingObject ={};
        $scope.dataObject = {};
        $scope.trigered = false;
        
        $scope.inProcess = false;
        
        $scope.regex = /\S/;
        $scope.regexMail = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
        
        $scope.style = '';
        $scope.dropStyle = '';

        $scope.checkStyle= function(data){

        	if(!data){
        	return $scope.style;
        	}else{
        		return '';
        	}
        }
        
        $scope.setDropStyle = function(object){

        	if(object.locations.locationsId === undefined || object.locations.locationsId.length == 0){
        		$scope.dropStyle = 'dropDownRed';
        		return;
        	}
        	
        	$scope.dropStyle = '';
        }
        
        $scope.dropDownEvent = {
        		onItemSelect:function(item){
        			$scope.dropStyle = '';
        		},
        		onItemDeselect:function(item){

        			if($scope.inProcess){
        				if($scope.dropDownModel === undefined || $scope.dropDownModel.length == 0){
        					$scope.dropStyle = 'dropDownRed';
        				}else{
                			$scope.dropStyle = '';
        				}
        			
        			}
        		},
        		onSelectAll:function(){
         		   $scope.dropStyle = '';
        		},	
        		onDeselectAll:function(){
        		if($scope.inProcess){
        		   $scope.dropStyle = 'dropDownRed';
        		}
        		}		
        }
        
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

        $scope.$watch(function(){
        }, function(newValue){
                console.log($rootScope.view_tab);
                console.log(newValue);
                self.fetchEverything();
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
                $scope.clear();
                $scope.style = '';
                $scope.inProcess = false;
            }, function () {
            	$scope.changeTrigered();
            	$scope.style = 'focusred';
                $scope.setDropStyle(toPass);
                $scope.inProcess = true;
            	//$scope.clear();
            });
        };

        $scope.clear = function () {
            $scope.addObjectInProcess = false;
            $scope.newObject = {active:"true"};
            $scope.dropDownModel = [];
            $scope.dropStyle = '';
            $scope.style = '';
            $scope.inProcess = false;
        };

        $scope.loadLocations = function (){
        	$scope.dropDownData = [];
        	AbstractService.fetchAll('/api/locations').then(function (response) {
        		for(var item in response){
        		//	if(response[item].active){
        			$scope.dropDownData.push({id:response[item].id, label: response[item].name + ' Fl. ' + response[item].floor});
        		//	}
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
            $scope.dropStyle = '';
            $scope.style = '';
            $scope.inProcess = false;
        };

        $scope.changeTrigered = function() {
        	$scope.trigered = !$scope.trigered;
        };
        
        $scope.editObject = function (key) {

        	$scope.editingObject.locations.locationsId = [];
        	for(item in $scope.dropDownModel){
        		$scope.editingObject.locations.locationsId.push($scope.dropDownModel[item].id);
        	}

        	//if(!($scope.editingObject.email === undefined)){
        		
            AbstractService.updateData('/api/vendors' + '/:documentId', $scope.editingObject).then(function (response) {
            console.log('element');
                console.log('response');
                console.log(response);
                console.log('element-after-copy');
                $scope.dataObject.list[key] = angular.copy(response);
                editingObject={};
                $scope.editingId = null;
                $scope.dropDownModel = [];
                $scope.style = '';
                $scope.inProcess = false;
                
            }, function () {
            	$scope.style = 'focusred';
            	$scope.changeTrigered();
                $scope.setDropStyle($scope.editingObject);
                $scope.inProcess = true;
            });
            
            
//        	}else{
//        		$scope.style = 'focusred';
//        		$scope.changeTrigered();
//        	}
        };
        
    }]);