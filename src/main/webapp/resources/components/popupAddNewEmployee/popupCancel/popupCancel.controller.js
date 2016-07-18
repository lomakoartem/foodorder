class popupCancelController {
	constructor() {
        this.canceled = false;

        this.changeCanceled = () => {
            this.canceled = !this.canceled;
        };

    }
}
export default popupCancelController;