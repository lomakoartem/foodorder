class popupCancelEditController {
	constructor() {
        this.canceled = false;

        this.changeCanceled = () => {
            this.canceled = !this.canceled;
        };

    }
}
export default popupCancelEditController;