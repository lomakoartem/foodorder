class sidebarController {
    constructor($rootScope, $location) {
        this.loggedIn = $rootScope.loggedIn;
        this.view_tab = 'locations';

        this.changeTab = (tab) => {
            this.view_tab = tab;
        };

        $rootScope.logOut = () => {
            $location.path('/logout');
            $rootScope.loggedIn = false;
        }
    }
}

sidebarController.$inject = ['$rootScope', '$location'];

export default sidebarController;