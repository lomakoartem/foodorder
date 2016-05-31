/**
 * Created by lomak on 29.05.2016.
 */
'use strict';

var abstractService = angular.module('AbstractServices', []);

abstractService.factory('DummyService', ['$filter', function ($filter) {
    var data = [];
    data.push({id: 0, value: 'borsch', price: 10},
        {id: 1, value: 'pizza', price: 20},
        {id: 2, value: 'borsch-1', price: 20});

    var service = {};
    service.addData = function (object) {
        object.id = data.length;
        if (!service.getById(object.id)) {
            data.push(object);
            return data;
        }
    };

    service.getById = function (idToSearch) {
        var result = [];
        angular.forEach(data, function (value, key) {
            if (value.id == idToSearch) {
                result.push(value);
            }
        });
        return result[0];
    };

    service.update = function (object) {
        var value = service.getById(object.id);
        if (angular.isDefined(value)) {
            value = object;
        }
        return data;
    };
    service.getAll = function () {
        return data;
    };
    return service;
}]);

abstractService.factory('AbstractService', ['$resource', '$q', '$timeout', 'DummyService', function ($resource, $q, $timeout, DummyService) {
    var service = {};
    var generateResource = function (address) {
        var resourceString = window.location + '/api/locations/' + address;
        return $resource(resourceString, {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
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

    service.fetchAll = function (address) {
        var resource = generateResource(address);
        var deferred = $q.defer();
        resource.query().$promise.then(function (response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function () {
            deferred.reject('error');
        });
        //$timeout(function () {
        //    deferred.resolve(DummyService.getAll());
        //}, 100);
        return deferred.promise;
    };

    service.addData = function (object) {
        var resource = generateResource('list', object);
        var deferred = $q.defer();
        resource.save(object).$promise.then(function (response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function () {
            deferred.reject('error');
        });
        return deferred.promise;
    };

    service.updateData = function (object) {
        var resource = generateResource('list/:documentId');
        var deferred = $q.defer();
        resource.update({documentId: object.id}, object).$promise.then(function (response) {
            console.log(response);
            deferred.resolve(JSON.parse(JSON.stringify(response)));
        }, function () {
            deferred.reject('error');
        });
        return deferred.promise;
    };

    return service;
}]);