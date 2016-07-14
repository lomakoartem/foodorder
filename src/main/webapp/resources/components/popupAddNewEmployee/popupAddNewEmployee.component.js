import template from "./popupAddNewEmployee.html"
import popupAddNewEmployeeController from './popupAddNewEmployee.controller';

const popupAddNewEmployeeComponent = {
    bindings: {
        changeTrigered: '=',
        addToList: '=',
        newEmployee: '=',
        emptyName: '=',
        emptyLink: '=',
        emptyFieldStyle: '='
    },
    template,
    controller: popupAddNewEmployeeController,
    controllerAs: 'popupAddNewEmployeeCtrl'
};

export default popupAddNewEmployeeComponent;