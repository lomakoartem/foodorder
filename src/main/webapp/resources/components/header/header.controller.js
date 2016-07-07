class headerController {
    constructor($location) {
        this.userNameOut = '';
        this._$location = $location;

        this.logOut = () => {
            $location.path('/login');
        }
    }

    isLoggedIn() {
        return ~this._$location.path().indexOf('login') ? false : true;
    }
}

headerController.$inject = ['$location'];

export default headerController;