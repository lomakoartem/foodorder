class popupRequiredController {
    constructor() {
        this.trigered = false;

        this.changeTrigered = () => {
            this.trigered = !this.trigered;
        };
    }
}

export default popupRequiredController;