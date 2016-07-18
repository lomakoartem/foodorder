import template from "./popupCancel.html"
import popupCancelController from './popupCancel.controller';

const popupCancelComponent = {
    bindings: {
        changeCanceled: '=',
        clickYes: '=',
        clickNo: '='
    },
    template,
    controller: popupCancelController,
    controllerAs: 'popupCancelCtrl'
};

export default popupCancelComponent;