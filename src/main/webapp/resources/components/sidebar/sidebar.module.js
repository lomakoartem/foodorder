import angular from 'angular';
import sidebarComponent from './sidebar.component';

const sidebarModule = angular.module('sidebar', [])
                             .component('sidebarComponent', sidebarComponent);

export default sidebarModule.name;