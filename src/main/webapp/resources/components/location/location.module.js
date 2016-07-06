import angular from 'angular';
import locationComponent from './location.component';

const locationModule = angular.module('location', [])
                              .component('locationComponent', locationComponent);

export default locationModule.name;