/**
 * Created by lomak on 29.05.2016.
 */
'use strict';

var abstractService = angular.module('AbstractServices', []);

abstractService.factory('AbstractService', ['$resource', '$q', function($resource, $q) {
    var service = {};
    var resourceString = 'http://' + location.host;
    // var resourceString = 'http://10.17.8.61:8081';
    var generateResource = function(address) {
        return $resource(resourceString + address, {}, {
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
            },
            'upload': {
                 method: 'POST',
                 transformRequest: angular.identity,
                 headers: {'Content-Type':undefined, enctype:'multipart/form-data'}
            }
        });
    };
    
    service.upload = function(address, object){
    	 var resource = generateResource(address);
         var deferred = $q.defer();
         resource.upload(object).$promise.then(function(response) {
             console.log(response);
             deferred.resolve(JSON.parse(JSON.stringify(response)));
         }, function() {
             deferred.reject('error');
         });
         return deferred.promise;
    }

    var generatePageResource = function(address) {
        return $resource(resourceString + address);
    };

    service.fetchPage = function(address) {
        var resource = generatePageResource(address);
        var deferred = $q.defer();
        resource.get().$promise.then(function(response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function() {
            deferred.reject('error');
        });
        return deferred.promise;
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
