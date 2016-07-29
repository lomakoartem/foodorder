class popupCancelEditController {
	constructor() {
        this.canceled = false;

        this.changeCanceled = () => {
        	console.log('popupCancelEditController change canceled');
            this.canceled = !this.canceled;
        };

    }
}
export default popupCancelEditController;