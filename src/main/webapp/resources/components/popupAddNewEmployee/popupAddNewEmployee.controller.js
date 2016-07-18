class popupAddNewEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.newEmployee = {active : 'true', admin : 'false'};
        this.regexName = /^\w+$/;
        this.emptyName = false;
        this.emptyLink = false;
        this.saved = false;
        this.duplicateName = false;
        this.duplicateLink = false;

        this.changeTrigered = () => {
            this.trigered = !this.trigered;
        };        

        this.checkName = (data) => {
            if (!data) {
                this.emptyName = true;
                this.duplicateName = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateName) {
                this.emptyName = false;
                return this.emptyFieldStyle;
            } else {
                this.emptyName = false;
                this.duplicateName = false;
                return '';
            }
        }

        this.checkLink = (data) => {
            if (!data) {
                this.emptyLink = true;
                this.duplicateLink = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateLink){
                this.emptyLink = false;
                return this.emptyFieldStyle;
            } else {
                this.emptyLink = false;
                this.duplicateLink = false;
                return '';
            }
        }

        this.checkStyle = (data) => {
            if(!data) {
                return this.emptyFieldStyle;
            } else {
                return '';
            }
        };

        this.clear = () => {
            this.duplicateLink = false;
            this.duplicateName = false;
            this.saved = false;
            this.emptyLink = false;
            this.emptyName = false;
            this.newEmployee = {active : 'true', admin : 'false'};
            this.emptyFieldStyle = '';
            this.changeTrigered();
        };

        this.cancel = () => {
            this.changeCanceled();
        }

        this.clickYes = () => {
            this.changeCanceled();
            this.clear();
        }

        this.clickNo = () => {
            this.changeCanceled();
        }
    }
}
export default popupAddNewEmployeeController;