'use strict';

import angular from 'angular';
import 'angular-route';
import 'angular-resource';
import mainConfig from './config/js/main.config';
import {host} from './config/js/constants';
import components from './components/components.module';

// Declare app level module which depends on views, and components
var FoodOrder = angular.module('FoodOrder', [
    'ngRoute',
    'ngResource',
    components
])
                       .constant('host', host)
                       .config(mainConfig);
