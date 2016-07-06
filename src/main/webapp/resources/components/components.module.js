import angular from 'angular';
import employee from './employee/employee.module';
import header from './header/header.module';
import location from './location/location.module';
import popupRequired from './popupRequired/popupRequired.module';
import sidebar from './sidebar/sidebar.module';
import vendor from './vendor/vendor.module';

const components = [
        employee,
        header,
        location,
        popupRequired,
        sidebar,
        vendor
    ],
    componentsModule = angular.module('components', components);

export default componentsModule.name;