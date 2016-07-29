import template from "./popupEditEmployee.html"
import popupEditEmployeeController from './popupEditEmployee.controller';

const popupEditEmployeeComponent = {
    bindings: {
    	changeTrigeredForEdit: '=',
    	editEmployee: '=', 
        emptyNameOnEdit: '=',
        emptyLinkOnEdit: '=',
        emptyFieldStyle: '=',
        checkNameOnEdit: '=',
        checkLinkOnEdit: '=',
        saved: '=',
        duplicateLinkOnEdit: '=',
        duplicateNameOnEdit: '='
    },
    template,
    controller: popupEditEmployeeController,
    controllerAs: 'popupEditEmployeeCtrl'
};

export default popupEditEmployeeComponent;