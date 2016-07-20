package ua.rd.foodorder.web.controller.validators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.rd.foodorder.web.dto.domain.VendorDTO;

@Component
public class VendorDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return VendorDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {

		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "additionalInfo", "additionalInfo.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");
		
		VendorDTO vendor = (VendorDTO)obj;
		List<Long> locationsId = vendor.getLocations().getLocationsId();
		if(locationsId.isEmpty()){
			e.rejectValue("locations.locationsId", "locations.locationsId.wrong", "wrong value for locationsId");
		}

	}
}
