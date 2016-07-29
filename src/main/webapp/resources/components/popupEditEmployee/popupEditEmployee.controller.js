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
        this.formTouchedOnEdit = false;

        this.changeTrigeredForEdit = (element) => {
            this.trigered = !this.trigered;
            
            if(element !== undefined){
                angular.copy(element, this.editingEmployee);
            	this.editingEmployee.active = element.active?'true':'false';
            	this.editingEmployee.admin = element.admin?'true':'false';
            }
        };      
        
        

        this.checkName = (data) => {
        	console.log('check name on edit');
            if (data.touched) {
            	console.log('data touched');
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
            	console.log('data not valid');
                this.emptyName = true;
                this.duplicateName = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateName) {
            	console.log('duplicate name');
                this.emptyName = false;
                return this.emptyFieldStyle;
            } else {
            	console.log('all is ok');
                this.emptyName = false;
                this.duplicateName = false;
                return '';
            }
        }

        this.checkLink = (data) => {
        	console.log('check link on edit');
            if (data.touched) {
            	console.log('data touched');
            	this.formTouchedOnEdit = true;
            }
            
            if (!data.valid) {
            	console.log('data not valid');
                this.emptyLink = true;
                this.duplicateLink = false;
                return this.emptyFieldStyle;
            } else if (this.duplicateLink){
            	console.log('duplicate name');
                this.emptyLink = false;
                return this.emptyFieldStyle;
            } else {
            	console.log('all is ok');
                this.emptyLink = false;
                this.duplicateLink = false;
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
            this.duplicateLink = false;
            this.duplicateName = false;
            this.saved = false;
            this.emptyLink = false;
            this.emptyName = false;
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