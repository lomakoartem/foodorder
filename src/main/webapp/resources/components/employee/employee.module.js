import angular from 'angular';
import employeeComponent from './employee.component';
import 'angular-utils-pagination';

const employeeModule = angular.module('employee', ['angularUtils.directives.dirPagination'])
                              .component('employeeComponent', employeeComponent);

export default employeeModule.name;