import angular from 'angular';
import loginComponent from './login.component';

const loginModule = angular.module('login', [])
                           .component('loginComponent', loginComponent);

export default loginModule.name;