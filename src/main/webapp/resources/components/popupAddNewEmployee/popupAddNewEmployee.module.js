import angular from 'angular';
import popupAddNewEmployeeService from './popupAddNewEmployee.service';
import popupAddNewEmployeeComponent from './popupAddNewEmployee.component';

const popupAddNewEmployeeModule = angular.module('popupAddNewEmployee', [])
									.service('popupAddNewEmployeeService', popupAddNewEmployeeService)
									.component('popupAddNewEmployeeComponent', popupAddNewEmployeeComponent);

export default popupAddNewEmployeeModule.name;
