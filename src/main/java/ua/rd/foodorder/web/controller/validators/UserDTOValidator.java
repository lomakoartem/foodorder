package ua.rd.foodorder.web.controller.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.rd.foodorder.web.dto.domain.UserDTO;

@Component
public class UserDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "active", "active.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "admin", "admin.empty");
        //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty");
    }
}
