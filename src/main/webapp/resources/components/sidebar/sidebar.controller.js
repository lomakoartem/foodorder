class sidebarController {
    constructor($location) {
        this._$location = $location;
    }
    
    isLoggedIn() {
        return ~this._$location.path().indexOf('login') ? false : true;
    }

    isLocations() {
        return ~this._$location.path().indexOf('locations') ? false : true;
    }

    isVendors() {
        return ~this._$location.path().indexOf('vendors') ? false : true;
    }

    isEmployees() {
        return ~this._$location.path().indexOf('employees') ? false : true;
    }
}

sidebarController.$inject = ['$location'];

export default sidebarController;