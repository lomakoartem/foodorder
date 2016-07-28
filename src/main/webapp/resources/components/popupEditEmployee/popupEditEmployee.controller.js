class popupEditEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.regexName = /^[\w \-]+$/;;
        this.regexLink = /\S+/;
        this.emptyName = false;
        this.emptyLink = false;
        this.saved = false;
        this.duplicateName = false;
        this.duplicateLink = false;
        this.formTouched = false;

        this.changeTrigeredForEdit = (element) => {
            this.trigered = !this.trigered;
            
            if(element !== undefined){
                angular.copy(element, this.editingEmployee);
            	this.editingEmployee.active = element.active?'true':'false';
            	this.editingEmployee.admin = element.admin?'true':'false';
            }
        };        

        this.checkName = (data) => {
            if (data.touched) this.formTouched = true;
            if (!data.valid) {
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
            if (data.touched) this.formTouched = true;
            if (!data.valid) {
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
            this.formTouched = false;
            this.duplicateLink = false;
            this.duplicateName = false;
            this.saved = false;
            this.emptyLink = false;
            this.emptyName = false;
            this.emptyFieldStyle = '';
            this.changeTrigeredForEdit();
        };

        this.cancel = () => {
            if (this.formTouched) {
                this.changeCanceled();
            }
            else {
                this.clear();
            }
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
export default popupEditEmployeeController;