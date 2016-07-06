import angular from 'angular';
import vendorComponent from './vendor.component';

require('./dropdown/angularjs-dropdown-multiselect');

const vendorModule = angular.module('vendor', ['angularjs-dropdown-multiselect'])
                          .component('vendorComponent', vendorComponent);

export default vendorModule.name;