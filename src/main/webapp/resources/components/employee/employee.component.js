import template from './employee.html';
import employeeController from './employee.controller';

const employeeComponent = {
    template,
    controller: employeeController,
    controllerAs: 'employeeCtrl'
};

export default employeeComponent;