import template from './header.html';
import headerController from './header.controller';

const headerComponent = {
    template,
    controller: headerController,
    controllerAs: 'headerCtrl'
};

export default headerComponent;