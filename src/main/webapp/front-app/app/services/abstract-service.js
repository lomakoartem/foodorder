/**
 * Created by lomak on 29.05.2016.
 */
'use strict';

var abstractService = angular.module('AbstractServices', []);

abstractService.factory('AbstractService', ['$resource', '$q', '$timeout', function($resource, $q, $timeout) {
    var service = {};
    var generateResource = function(address) {
        var resourceString = 'http://' + location.host + address;
        // var resourceString = 'http://10.17.8.61:8081' + address;
        return $resource(resourceString, {}, {
            'get': {
                method: 'GET',
                transformResponse: function(data) {
                    return angular.fromJson(data).list
                },
                isArray: true
            },
            'save': {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-Requested-With': 'XMLHttpRequest'
                }
            },
            'update': {
                method: 'PUT',
                params: {documentId: '@documentId'}
            }
        });
    };

    service.fetchAll = function(address) {
        var resource = generateResource(address);
        var deferred = $q.defer();
        resource.query().$promise.then(function(response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function() {
            deferred.reject('error');
        });
        return deferred.promise;
    };

    service.addData = function(address, object) {
        var resource = generateResource(address, object);
        var deferred = $q.defer();
        resource.save(object).$promise.then(function(response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function() {
            deferred.reject('error');
        });
        return deferred.promise;
    };

    service.updateData = function(address, object) {
        var resource = generateResource(address);
        var deferred = $q.defer();
        resource.update({documentId: object.id}, object).$promise.then(function(response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function() {
            deferred.reject('error');
        });
        return deferred.promise;
    };

    return service;
}]);
