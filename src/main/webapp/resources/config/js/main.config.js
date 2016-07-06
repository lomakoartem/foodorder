function mainConfig($routeProvider, $httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    $routeProvider.when('/list/locations', {
        templateUrl: 'resources/views/location.page.html'
    }).when('/list/vendors', {
        templateUrl: 'resources/views/vendor.page.html'
    }).when('/list/employees', {
        templateUrl: 'resources/views/employee.page.html',
        reloadOnSearch: false
    }).when('/login', {
        templateUrl: 'front-app/app/login/login-page.html',
        controller: 'LoginCtrl',
        controllerAs: 'loginCtrl'
    }).otherwise({
        redirectTo: '/login'
    });
}

mainConfig.$inject = ['$routeProvider', '$httpProvider'];

export default mainConfig;