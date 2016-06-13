package ua.rd.foodorder.web.controller.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.domain.VendorDto;

/**
 * Created by Artem_Lomako on 6/7/2016.
 */
@Component
public class VendorDtoValidator implements Validator {

	public boolean supports(Class clazz) {
		return VendorDto.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {

		ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
//		ValidationUtils.rejectIfEmptyOrWhitespace(e, "phone", "phone.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "email.empty");

	}
}
