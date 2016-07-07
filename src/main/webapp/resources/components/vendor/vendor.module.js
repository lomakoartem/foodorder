import angular from 'angular';
import vendorService from './vendor.service';
import vendorComponent from './vendor.component';

require('./dropdown/angularjs-dropdown-multiselect');

const vendorModule = angular.module('vendor', ['angularjs-dropdown-multiselect'])
                            .service('vendorService', vendorService)
                            .component('vendorComponent', vendorComponent);

export default vendorModule.name;