class popupAddNewEmployeeService {
    constructor($resource, $q, host) {
        this.resourceString = host.resourceString;
        let generateResource = (address) => {
                return $resource(this.resourceString + address, {}, {
                    'get': {
                        method: 'GET',
                        transformResponse: (data) => {
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
                        params: {
                            documentId: '@documentId'
                        }
                    },
                    'upload': {
                        method: 'POST',
                        transformRequest: angular.identity,
                        headers: {
                            'Content-Type': undefined,
                            enctype: 'multipart/form-data'
                        }
                    }
                });
            };
            
            this.addData = (address, object) => {
                let resource = generateResource(address, object),
                    deferred = $q.defer();
                resource.save(object).$promise.then((response) => {
                    deferred.resolve(JSON.parse(JSON.stringify(response)));
                }, () => {
                    deferred.reject('error');
                });
                return deferred.promise;
            };
        
    }
}

popupAddNewEmployeeService.$inject = ['$resource', '$q', 'host'];

export default popupAddNewEmployeeService;
