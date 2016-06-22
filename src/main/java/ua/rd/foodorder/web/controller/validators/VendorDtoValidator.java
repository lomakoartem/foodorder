package ua.rd.foodorder.web.controller.validators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.rd.foodorder.web.dto.domain.VendorDTO;

/**
 * Created by Artem_Lomako on 6/7/2016.
 */
@Component
public class VendorDtoValidator implements Validator {

	public boolean supports(Class clazz) {
		return VendorDTO.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {

		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "additionalInfo", "additionalInfo.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		//ValidationUtils.rejectIfEmpty(e, "locationsId", "locationsId.empty");
		
		VendorDTO vendor = (VendorDTO)obj;
		List<Long> locationsId = vendor.getLocations().getLocationsId();
		if(locationsId.isEmpty()){
			e.rejectValue("locationsId", "locationsId.wrong", "wrong value for locationsId");
		}

	}
}
