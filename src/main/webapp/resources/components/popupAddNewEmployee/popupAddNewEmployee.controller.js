class popupAddNewEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.newEmployee = {active : 'true', admin : 'false'};
        this.regexName = /^\w+$/;
        this.emptyName = false;
        this.emptyLink = false;


        this.changeTrigered = () => {
            this.trigered = !this.trigered;
        };

        this.checkName = (data) => {
            if (!data) {
                this.emptyName = true;
                return this.emptyFieldStyle;
            } else {
                this.emptyName = false;
                return '';
            }
        }

        this.checkLink = (data) => {
            if (!data) {
                this.emptyLink = true;
                return this.emptyFieldStyle;
            } else {
                this.emptyLink = false;
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
            this.emptyLink = false;
            this.emptyName = false;
            this.newEmployee = {active : 'true', admin : 'false'};
            this.emptyFieldStyle = '';
            this.changeTrigered();
        };
    }
}
export default popupAddNewEmployeeController;