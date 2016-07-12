class popupAddNewEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.newEmployee = {active : 'true', admin : 'false'};
        this.regexName = /^\w+$/;
        this.style = '';
        
        this.changeTrigered = () => {
            this.trigered = !this.trigered;
        };
        
        this.checkStyle = (data) => {
            if(!data) {
                return this.style;
            } else {
                return '';
            }
        };
    }
}
export default popupAddNewEmployeeController;