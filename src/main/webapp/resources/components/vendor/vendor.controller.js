class vendorController {
    constructor($scope, vendorService) {
        let self = this;
        this.editingId = null;
        this.vendorsWithCredentials = null;
        this.newObject = {
            active: 'true'
        };
        this.editingObject = {};
        this.dataObject = {};
        this.trigered = false;
        this.inProcess = false;
        this.regex = /\S/;
        this.regexMail = /^[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$/i;
        this.style = '';
        this.dropStyle = '';

        this.generateAndSendDisabled = false;
        this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
        this.generateAndSendVendorStyle = 'table-view__body-btn--send';
        this.generateAndSendVendorIconSelected = 'glyphicon-refresh';
        this.generateAndSendVendorIcon = 'glyphicon-refresh';
        this.otherObjectEdited = false;
        
        this.checkStyle = (data) => {
            if(!data) {
                return this.style;
            } else {
                return '';
            }
        };
        
        this.generateStyleForSendMail = (selected, generated) =>{
        	if(selected){
        		if(generated){
        			return this.generateAndSendVendorStyleSelected; 
        		}else{
        			return 'table-view__body-btn--send-fail';
        		}	
        	}else{
        		if(generated){
        			return this.generateAndSendVendorStyle; 
        		}else{
        	        return 'table-view__body-btn--send-fail';
        		}	
        	}
        }

        this.setDropStyle = (object) => {

            if(object.locations.locationsId === undefined || object.locations.locationsId.length == 0) {
                this.dropStyle = 'dropDownRed';
                return;
            }

            this.dropStyle = '';
        };

        this.dropDownEvent = {
            onItemSelect: (item) => {
                this.dropStyle = '';
            },
            onItemDeselect: (item) => {
                if(this.inProcess) {
                    if(this.dropDownModel === undefined || this.dropDownModel.length == 0) {
                        this.dropStyle = 'dropDownRed';
                    } else {
                        this.dropStyle = '';
                    }

                }
            },
            onSelectAll: () => {
                this.dropStyle = '';
            },
            onDeselectAll: () => {
                if(this.inProcess) {
                    this.dropStyle = 'dropDownRed';
                }
            }
        };

        this.dropDownModel = [];
        this.dropDownSettings = {
            scrollableHeight: '200px',
            scrollable: true,
            closeOnBlur: true,
            smartButtonMaxItems: 1,
            smartButtonTextConverter: (itemText, originalItem) => {
                return itemText;
            }
        };

        this.dropDownData = [];

        $scope.$watch(() => {
        }, (newValue) => {
            self.fetchEverything();
        });

        self.fetchEverything = () => {
        	
        	this.loadCredentials();
        	
            vendorService.fetchAll('/api/vendors').then((response) => {
                this.dataObject.list = response;
            }, (errResponse) => {
                console.error('Error while fetching vendors');
            });
        };
        
        this.loadCredentials = () => {
        	vendorService.fetchAll('/api/vendors/credentials').then((response) =>{
        		this.vendorsWithCredentials = response;
        	}, (errResponse) => {
        		console.error('Error while fetching credentials');
        	}
        	);
        };

        this.addObjectInProcess = false;
        this.addingObject = () => {
            this.cancel();
            this.addObjectInProcess = true;
            this.loadLocations();
        };

        this.addToList = (value) => {
            this.newObject.locations = {locationsId: []};
            for(let item in this.dropDownModel) {
                this.newObject.locations.locationsId.push(this.dropDownModel[item].id);
            }

            let toPass = (angular.isDefined(value)) ? value : this.newObject;
            vendorService.addData('/api/vendors', toPass).then((response) => {
                this.dataObject.list.push(response);
                this.newObject = {active: 'true'};
                this.clear();
                this.style = '';
                this.inProcess = false;
            }, () => {
                this.changeTrigered();
                this.style = 'focusred';
                this.setDropStyle(toPass);
                this.inProcess = true;
            });
        };

        this.clear = () => {
            this.addObjectInProcess = false;
            this.newObject = {active: 'true'};
            this.dropDownModel = [];
            this.dropStyle = '';
            this.style = '';
            this.inProcess = false;
            this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
            this.generateAndSendVendorIconSelected = 'glyphicon-refresh';
        	this.otherObjectEdited = true;
        };

        this.loadLocations = () => {
            this.dropDownData = [];
            vendorService.fetchAll('/api/locations/active').then((response) => {
                for(let item in response) {
                    this.dropDownData.push({
                        id: response[item].id,
                        label: response[item].name + ' Fl. ' + response[item].floor
                    });
                }

            }, (errResponse) => {
                console.error('Error while fetching locations');
            });

        };

        this.editing = (object) => {
            this.loadLocations();
            this.addObjectInProcess = false;
            this.clear();
            this.editingObject = angular.copy(object);

            for(let item in this.editingObject.locations.locationsId) {
                this.dropDownModel.push({'id': this.editingObject.locations.locationsId[item]});
            }

            this.editingObject.active = this.editingObject.active + '';
            this.editingId = object.id;

        };

        this.cancel = (object) => {
            this.editingId = null;
            this.dropDownModel = [];
            this.dropStyle = '';
            this.style = '';
            this.inProcess = false;
            this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
            this.generateAndSendVendorIconSelected = 'glyphicon-refresh';
        };
        
        this.isGenerateAndSendDisabled = (id) => {
        	
        	if(id != this.editingId){
        		return true;
        	}
        	
        	return this.generateAndSendDisabled;
        }
        
        this.generateAndSend = (object) => {
        	
        //	this.generateAndSendDisabled = true;
        	this.generateAndSendVendorIconSelected = 'glyphicon-hourglass';
        	this.otherObjectEdited = false;
        	
        	vendorService.updateData('/api/vendors/generatePassword' + '/:documentId', this.editingObject).then((response) => {
        		if(this.otherObjectEdited == false){
        			this.generateAndSendVendorIconSelected = 'glyphicon-ok';
            		this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
        		};
        		
        		//this.generateAndSendDisabled = false;
        		this.loadCredentials();
            }, () => {
            	
            	if(this.otherObjectEdited == false){
            		this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send-fail';
            		this.generateAndSendVendorIconSelected = 'glyphicon-remove';
            	}
            	
            	this.generateAndSendDisabled = false;
            });
        };

        this.editObject = (key) => {

            this.editingObject.locations.locationsId = [];
            for(let item in this.dropDownModel) {
                this.editingObject.locations.locationsId.push(this.dropDownModel[item].id);
            }

            vendorService.updateData('/api/vendors' + '/:documentId', this.editingObject).then((response) => {
                this.dataObject.list[key] = angular.copy(response);
                this.editingObject = {};
                this.editingId = null;
                this.dropDownModel = [];
                this.style = '';
                this.inProcess = false;
                this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
                this.generateAndSendVendorIconSelected = 'glyphicon-refresh';
            }, () => {
                this.style = 'focusred';
                this.changeTrigered();
                this.setDropStyle(this.editingObject);
                this.inProcess = true;
                this.generateAndSendVendorStyleSelected = 'table-view__body-btn--send';
                this.generateAndSendVendorIconSelected = 'glyphicon-refresh';
            });
        };
    }
}

vendorController.$inject = ['$scope', 'vendorService'];

export default vendorController;