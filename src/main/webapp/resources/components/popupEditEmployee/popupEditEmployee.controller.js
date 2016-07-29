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
        	console.log('check name on edit');
            if (data.touched) {
            	console.log('data touched');
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
            	console.log('data not valid');
                this.emptyNameOnEdit = true;
                this.duplicateNameOnEdit = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateNameOnEdit) {
            	console.log('duplicate name');
                this.emptyNameOnEdit = false;
                return this.emptyFieldStyle;
            } else {
            	console.log('all is ok');
                this.emptyNameOnEdit = false;
                this.duplicateNameOnEdit = false;
                return '';
            }
        }

        this.checkLinkOnEdit = (data) => {
        	console.log('check link on edit');
            if (data.touched) {
            	console.log('data touched');
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
            	console.log('data not valid');
                this.emptyLinkOnEdit = true;
                this.duplicateLinkOnEdit = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateLinkOnEdit){
            	console.log('duplicate name');
                this.emptyLinkOnEdit = false;
                return this.emptyFieldStyle;
            } else {
            	console.log('all is ok');
                this.emptyLinkOnEdit = false;
                this.duplicateLinkOnEdit = false;
                return '';
            }
        }

        this.checkStyle = (data) => {
        	console.log('check style on edit');
            if(!data) {
            	console.log('return emptyFieldStyle');
                return this.emptyFieldStyle;
            } else {
            	console.log('return empty');
                return '';
            }
        };

        this.clear = () => {
        	console.log('clear on edit');
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
        	console.log('cancel on edit');
            if (this.formTouchedOnEdit) {
                this.changeCanceled();
            }
            else {
                this.clear();
            }
        }

        this.clickYes = () => {
        	console.log('i click yes on edit');
            this.changeCanceled();
            this.clear();
        }

        this.clickNo = () => {
        	console.log('i click no on edit');
            this.changeCanceled();
        }
    }
}
export default popupEditEmployeeController;