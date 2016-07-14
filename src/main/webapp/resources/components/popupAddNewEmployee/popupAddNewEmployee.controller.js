class popupAddNewEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.newEmployee = {active : 'true', admin : 'false'};
        this.regexName = /^\w+$/;
        this.style = '';
        this.emptyName = false;
        
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