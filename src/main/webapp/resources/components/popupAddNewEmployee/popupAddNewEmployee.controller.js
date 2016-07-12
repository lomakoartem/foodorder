class popupAddNewEmployeeController {
	constructor(popupAddNewEmployeeService) {
        this.trigered = false;
        this.editingEmployee = {};
        this.dataObject = {};
        this.newEmployee = {active : 'true', adminRights : 'false'};
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
popupAddNewEmployeeController.$inject = ['popupAddNewEmployeeService'];
export default popupAddNewEmployeeController;
