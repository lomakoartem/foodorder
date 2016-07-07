import angular from 'angular';
import locationService from './location.service';
import locationComponent from './location.component';

const locationModule = angular.module('location', [])
                              .service('locationService', locationService)
                              .component('locationComponent', locationComponent);

export default locationModule.name;