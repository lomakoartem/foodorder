class popupRequiredController {
    constructor() {
        this.trigered = false;

        this.changeTrigered = () => {
            this.trigered = !this.trigered;
        };
    }
}

popupRequiredController.$inject = [];

export default popupRequiredController;