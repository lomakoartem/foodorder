import angular from 'angular';
import popupEditEmployeeComponent from './popupEditEmployee.component';

const popupEditEmployeeModule = angular.module('popupEditEmployee', [])
									.component('popupEditEmployeeComponent', popupEditEmployeeComponent);

export default popupEditEmployeeModule.name;