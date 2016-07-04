import angular from 'angular';
import header from './header/header.module';

const components = [
        header
    ],
    componentsModule = angular.module('components', components);

export default componentsModule.name;