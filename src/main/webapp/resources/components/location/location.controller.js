class locationController {
    constructor($scope, AbstractService) {
        let self = this;
        this.editingId = null;
        this.newObject = {active: 'true'};
        this.editingObject = {};
        this.dataObject = {};
        this.trigered = false;
        this.style = '';
        this.regex = /\S/;
        this.regexNumber = /^([1-9]|[1-4]\d|50)$/;
        this.regexName = /^\w+$/;
        this.addObjectInProcess = false;

        this.checkStyle = (data) => {
            if(!data) {
                return this.style;
            } else {
                return '';
            }
        };

        $scope.$watch(() => {
        }, (newValue) => {
            self.fetchEverything();
        });

        self.fetchEverything = () => {
            AbstractService.fetchAll('/api/locations').then((response) => {
                this.dataObject.list = response;
            }, () => {
                console.error('Error while fetching food');
            });
        };

        this.addingObject = () => {
            this.cancel();
            this.addObjectInProcess = true;
        };

        this.addToList = (value) => {
            let toPass = (angular.isDefined(value)) ? value : this.newObject;
            AbstractService.addData('/api/locations', toPass).then((response) => {
                this.dataObject.list.push(response);
                this.newObject = {active: 'true'};
                this.style = '';
                this.addObjectInProcess = false;
            }, () => {
                //this.clear();
                this.changeTrigered();
                this.style = 'focusred';
            });
        };

        this.clear = () => {
            this.addObjectInProcess = false;
            this.newObject = {active: 'true'};
            this.style = '';
        };

        this.editing = (object) => {
            this.addObjectInProcess = false;
            this.clear();
            this.editingObject = angular.copy(object);
            this.editingObject.active = this.editingObject.active + '';
            this.editingId = object.id;
        };

        this.cancel = () => {
            this.editingId = null;
        };

        this.editObject = (key) => {
            AbstractService.updateData('/api/locations' + '/:documentId', this.editingObject).then((response) => {
                this.dataObject.list[key] = angular.copy(response);
                this.editingObject = {};
                this.editingId = null;
                this.style = '';
            }, () => {
                this.style = 'focusred';
                this.changeTrigered();
            });
        };
    }
}

locationController.$inject = ['$scope', 'AbstractService'];

export default locationController;