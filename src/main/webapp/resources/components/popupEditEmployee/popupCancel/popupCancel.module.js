import angular from 'angular';
import popupCancelComponent from './popupCancel.component';

const popupCancelModule = angular.module('popupCancel', [])
									.component('popupCancelComponent', popupCancelComponent);

export default popupCancelModule.name;