import angular from 'angular';
import headerComponent from './header.component';

const headerModule = angular.module('header', [])
                            .component('headerComponent', headerComponent);

export default headerModule.name;