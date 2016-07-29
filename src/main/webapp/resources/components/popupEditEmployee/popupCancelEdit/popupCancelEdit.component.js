import template from "./popupCancelEdit.html"
import popupCancelEditController from './popupCancelEdit.controller';

const popupCancelEditComponent = {
    bindings: {
        changeCanceled: '=',
        clickYes: '=',
        clickNo: '='
    },
    template,
    controller: popupCancelEditController,
    controllerAs: 'popupCancelEditCtrl'
};

export default popupCancelEditComponent;