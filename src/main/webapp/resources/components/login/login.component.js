import template from './login.html';
import loginController from './login.controller';

const loginComponent = {
    template,
    controller: loginController,
    controllerAs: 'loginCtrl'
};

export default loginComponent;