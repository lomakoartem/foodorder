import template from './popupRequired.html';
import popupRequiredController from './popupRequired.controller';

const popupRequiredComponent = {
    bindings: {
        changeTrigered: '='
    },
    template,
    controller: popupRequiredController,
    controllerAs: 'popupReqCtrl'
};

export default popupRequiredComponent;