import angular from 'angular';
import popupRequiredComponent from './popupRequired.component';

const popupRequiredModule = angular.module('popupRequired', [])
                                   .component('popupRequiredComponent', popupRequiredComponent);

export default popupRequiredModule.name;