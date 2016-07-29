import angular from 'angular';
import popupCancelEditComponent from './popupCancelEdit.component';

const popupCancelEditModule = angular.module('popupCancelEdit', [])
									.component('popupCancelEditComponent', popupCancelEditComponent);

export default popupCancelEditModule.name;