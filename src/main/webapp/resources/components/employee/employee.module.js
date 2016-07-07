import angular from 'angular';
import employeeService from './employee.service';
import employeeComponent from './employee.component';
import 'angular-utils-pagination';

const employeeModule = angular.module('employee', ['angularUtils.directives.dirPagination'])
                              .service('employeeService', employeeService)
                              .component('employeeComponent', employeeComponent);

export default employeeModule.name;