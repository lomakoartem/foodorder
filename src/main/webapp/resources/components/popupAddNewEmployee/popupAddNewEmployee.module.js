import angular from 'angular';
import popupAddNewEmployeeComponent from './popupAddNewEmployee.component';

const popupAddNewEmployeeModule = angular.module('popupAddNewEmployee', [])
									.component('popupAddNewEmployeeComponent', popupAddNewEmployeeComponent);

export default popupAddNewEmployeeModule.name;