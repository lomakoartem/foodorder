class loginController {
    constructor($location) {
        this.user = {};
        this.login = () => {
            $location.path('/list/locations');
        }
    }
}

loginController.$inject = ['$location'];

export default loginController;