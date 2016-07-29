class popupEditEmployeeController {
	constructor() {
        this.trigered = false;
        this.editingEmployee = {};
        this.regexName = /^[\w \-]+$/;;
        this.regexLink = /\S+/;
        this.emptyNameOnEdit = false;
        this.emptyLinkOnEdit = false;
        this.saved = false;
        this.duplicateNameOnEdit = false;
        this.duplicateLinkOnEdit = false;
        this.formTouchedOnEdit = false;

        this.changeTrigeredForEdit = (element) => {
            this.trigered = !this.trigered;
            
            if(element !== undefined){
                angular.copy(element, this.editingEmployee);
            	this.editingEmployee.active = element.active?'true':'false';
            	this.editingEmployee.admin = element.admin?'true':'false';
            }
        };      

        this.checkNameOnEdit = (data) => {
            if (data.touched) {
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
                this.emptyNameOnEdit = true;
                this.duplicateNameOnEdit = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateNameOnEdit) {
                this.emptyNameOnEdit = false;
                return this.emptyFieldStyle;
            } else {
                this.emptyNameOnEdit = false;
                this.duplicateNameOnEdit = false;
                return '';
            }
        }

        this.checkLinkOnEdit = (data) => {
            if (data.touched) {
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
                this.emptyLinkOnEdit = true;
                this.duplicateLinkOnEdit = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateLinkOnEdit){
                this.emptyLinkOnEdit = false;
                return this.emptyFieldStyle;
            } else {
                this.emptyLinkOnEdit = false;
                this.duplicateLinkOnEdit = false;
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
            this.formTouchedOnEdit = false;
            this.duplicateLinkOnEdit = false;
            this.duplicateNameOnEdit = false;
            this.saved = false;
            this.emptyLinkOnEdit = false;
            this.emptyNameOnEdit = false;
            this.emptyFieldStyle = '';
            this.changeTrigeredForEdit();
        };

        this.cancel = () => {
            if (this.formTouchedOnEdit) {
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