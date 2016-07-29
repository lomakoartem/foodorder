class employeeController {
    constructor($scope, $location, employeeService) {
        let self = this;
        this.editingId = null;
        this.editingKey = null;
        this.newObject = {
            active: 'true'
        };
        this.editingObject = {};
        this.dataObject = {};
        this.trigered = false;
        this.emptyFieldStyle = '';
        this.controlPageSize = 7;
        this.checkboxShowAll = {
            value: false
        };
        this.searchFlag = false;
        this.searchIsEmpty = {
            empty: true
        };
        this.users = [];
        this.totalUsers = 0;
        this.usersPerPage = 20; // this should match however many results
        // your API puts on one page
        
        this.totalPages = 0;
        this.searchTerm = '';
        this.pagination = {
            current: 1
        };

        this.addingObject = () => {
        	this.changeTrigered();
        };
        
        this.editingObject = (key, element) => {
        	this.changeTrigeredForEdit(element);
        	this.editingKey = key;
        }
        
        this.pageChanged = (newPage) => {
            this.getResultsPage(newPage);
            let myDiv = document.getElementById('scrTop');
            myDiv.scrollTop = 0;
        };

        this.getResultsPage = (pageNumber) => {
            if(!this.searchFlag) {
                this.fetchData('/api/employees/pages/' + pageNumber + '?size=' + this.usersPerPage);
            } else {
                this.fetchData('/api/employees/search/' + this.searchTerm + '?pageNumber=' + pageNumber + '&size=' + this.usersPerPage);
            }
            this.pagination.current = pageNumber;
            $location.search('page', pageNumber);
            $location.search('all', null);
        };

        this.fetchData = (requestString) => {
            employeeService.fetchPage(requestString).then((response) => {

                this.users = response.content;
                this.totalUsers = response.totalElements;
                this.totalPages = response.totalPages;

                if(response.content == 0 && this.searchFlag) {
                    this.searchIsEmpty.empty = false;
                } else {
                    this.searchIsEmpty.empty = true;
                }

            }, () => {
                console.error('Error while fetching employees');
            });
        };

        this.findEmployees = () => {

            if(this.searchTerm !== undefined && (this.searchTerm != null || this.searchTerm != '') && this.searchTerm.length >= 3) {

                this.searchFlag = true;
                $location.search('page', 1);
                $location.search('search', this.searchTerm);

                if(this.searchTerm.split(' ').length >= 3) {
                    this.searchIsEmpty.empty = false;
                } else {
                    self.getCorrectView(1);
                }

            } else {
                if(this.searchFlag) {

                    $location.search('search', null);
                    this.searchFlag = false;
                    this.searchIsEmpty.empty = true;
                    self.getCorrectView(1);

                }
            }

        };

        self.getCorrectView = (page) => {
        	console.log(this.users);
            if(this.checkboxShowAll.value && this.searchFlag == false) {
                self.fetchEverything();
            } else if(this.checkboxShowAll.value == false && this.searchFlag == false) {
            	this.usersPerPage = 20;
                this.getResultsPage(page);
            } else if(this.checkboxShowAll.value && this.searchFlag) {
                self.fetchFound(this.searchTerm);
            } else if(this.checkboxShowAll.value == false && this.searchFlag) {
            	this.usersPerPage = 20;
                this.getResultsPage(page);
            }
        };
        
        self.fetchEverything = () => {

            $location.search('page', null);
            $location.search('search', null);
            $location.search('all', true);

            employeeService.fetchAll('/api/employees').then((response) => {
                this.users = response;
                this.totalUsers = this.users.length;
                this.usersPerPage = this.users.length;
                this.totalPages = 1;

            }, (errResponse) => {
                console.error('Error while fetching employees');
            });
        };

        self.fetchFound = (searchTerm) => {

            $location.search('page', null);
            $location.search('search', searchTerm);
            $location.search('all', true);

            employeeService.fetchAll('/api/employees/searchAll/' + searchTerm).then((response) => {
                this.users = response;
                this.totalUsers = this.users.length;
                this.usersPerPage = this.users.length;
                this.totalPages = 1;

                if(this.users.length == 0) {
                    this.searchIsEmpty.empty = false;
                } else {
                    this.searchIsEmpty.empty = true;
                }

            }, (errResponse) => {
                console.error('Error while fetching employees');
            });
        };

        this.clickCheckboxShowAll = () => {
            self.getCorrectView(1);
        };

        this.sendFile = () => {
            let formData = new FormData();
            formData.append('file', file.files[0]);

            employeeService.upload('/api/employees/upload', formData).then((response) => {
                this.getResultsPage(1);
            }, (errResponse) => {
                console.error('Error while uploading employees from file');
            });
        };

        this.checkStyle = (data) => {

            if(!data) {
                return this.emptyFieldStyle;
            } else {
                return '';
            }
        };

        this.regex = /\S/;
        this.regexNumber = /^([1-9]|[1-4]\d|50)$/;

        $scope.$watch(() => {
        }, () => {

            if($location.search().search !== undefined) {
                this.searchTerm = $location.search().search;
                this.searchFlag = true;
            }

            if($location.search().all !== undefined) {
                this.checkboxShowAll.value = $location.search().all;
            }

            if($location.search().page !== undefined) {
                self.getCorrectView($location.search().page);
            } else {
                self.getCorrectView(1);
            }
        });

        this.duplicateNameErrorCode = 6;
        this.duplicateLinkErrorCode = 7;
        this.duplicateNameAndLinkErrorCode = 8;
        
        this.editEmployee = (value) => {
        	this.savedOnEdit = true;
            if (!this.emptyName && !this.emptyLink) {
            	employeeService.updateData('/api/employees' + '/:documentId', value).then((response) => {
	                this.users[this.editingKey] = angular.copy(response);
	                this.editingId = null;
	                this.style = '';
	                this.editingKey = null;
	                this.changeTrigeredForEdit();
	                this.emptyFieldStyle = '';
	                this.savedOnEdit = false;
	            }, (response) => {
	            	console.log(response);
	                let errorCode = response.data.code;
	                if (errorCode == this.duplicateNameErrorCode) {
	                    this.emptyNameOnEdit = false;
	                    this.duplicateNameOnEdit = true;
	                    this.duplicateLinkOnEdit = false;
	                    this.emptyLinkOnEdit = false;
	                } else if (errorCode == this.duplicateLinkErrorCode) {
	                	this.emptyNameOnEdit = false;
	                    this.duplicateNameOnEdit = false;
	                    this.emptyLinkOnEdit = false;
	                    this.duplicateLinkOnEdit = true;
	                } else if (errorCode == this.duplicateNameAndLinkErrorCode) {
	                    this.emptyNameOnEdit = false;
	                    this.emptyLinkOnEdit = false;
	                    this.duplicateNameOnEdit = true;
	                    this.duplicateLinkOnEdit = true;
	                }
	                this.emptyFieldStyleOnEdit = 'focusred';
	            });
            }else{
            	
            }
        }
        
        this.addToList = (value) => {
            this.saved = true;
            let toPass = (angular.isDefined(value)) ? value : this.newEmployee;
            if (!this.emptyName && !this.emptyLink) {
                employeeService.addData('/api/employees' + '?size=' + this.usersPerPage, toPass).then((response) => {
                    this.users = response.content;
                    this.totalUsers = response.totalElements;
                    this.totalPages = response.totalPages;
                    this.pagination.current = response.number + 1;
                    $location.search('page', response.number + 1);
                    this.newEmployee = {active: 'true', admin: 'false'};
                    this.changeTrigered();
                    this.emptyFieldStyle = '';
                    this.saved = false;
                }, (response) => {
                    console.log(response);
                    let errorCode = response.data.code;
                    if (errorCode == this.duplicateNameErrorCode) {
                        this.emptyName = false;
                        this.duplicateName = true;
                        this.duplicateLink = false;
                        this.emptyLink = false;
                    } else if (errorCode == this.duplicateLinkErrorCode) {
                    	this.emptyName = false;
                        this.duplicateName = false;
                        this.emptyLink = false;
                        this.duplicateLink = true;
                    } else if (errorCode == this.duplicateNameAndLinkErrorCode) {
                        this.emptyName = false;
                        this.emptyLink = false;
                        this.duplicateName = true;
                        this.duplicateLink = true;
                    }
                    this.emptyFieldStyle = 'focusred';
                });
            } else {
                this.emptyFieldStyle = 'focusred';
            }
    	};
	}
}

employeeController.$inject = ['$scope', '$location', 'employeeService'];

export default employeeController;