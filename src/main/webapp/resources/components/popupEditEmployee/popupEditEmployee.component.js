import template from "./popupEditEmployee.html"
import popupEditEmployeeController from './popupEditEmployee.controller';

const popupEditEmployeeComponent = {
    bindings: {
    	changeTrigeredForEdit: '=',
    	editEmployee: '=', 
        emptyName: '=',
        emptyLink: '=',
        emptyFieldStyle: '=',
        checkName: '=',
        checkLink: '=',
        saved: '=',
        duplicateLink: '=',
        duplicateName: '='
    },
    template,
    controller: popupEditEmployeeController,
    controllerAs: 'popupEditEmployeeCtrl'
};

export default popupEditEmployeeComponent;