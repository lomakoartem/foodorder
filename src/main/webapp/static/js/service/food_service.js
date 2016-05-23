'use strict';
 
App.factory('FoodService', ['$http', '$q', function($http, $q){
 
    return {
            fetchAllFood: function() {
                    return $http.get('http://localhost:8081/foodorder/rest/food/list')
                            .then(
                                    function(response){
                                        return response.data;
                                    }, 
                                    function(errResponse){
                                        console.error('Error while fetching food');
                                        return $q.reject(errResponse);
                                    }
                            );
            }
         
    };
 
}]);