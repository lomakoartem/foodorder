package ua.rd.foodorder.web.controller.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.rd.foodorder.domain.Location;

@Component
public class LocationValidator implements Validator {

	public boolean supports(Class clazz) {
		return Location.class.equals(clazz);
	}

	public void validate(Object obj, Errors e) {

		ValidationUtils.rejectIfEmpty(e, "name", "name.empty");
		ValidationUtils.rejectIfEmpty(e, "address", "address.empty");
		ValidationUtils.rejectIfEmpty(e, "info", "info.empty");
	}
}
