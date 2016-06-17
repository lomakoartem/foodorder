package ua.rd.foodorder.web.controller.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ua.rd.foodorder.domain.Location;

@Component
public class LocationValidator implements Validator {
    @Override
    public boolean supports(Class clazz) {
        return Location.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

        ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "name.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "address", "address.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "floor", "floor.empty");

        Location location = (Location) obj;
        Integer floor = location.getFloor();
        if (floor == null || floor > 50 || floor <= 0) {
            e.rejectValue("floor", "floor.wrong", "wrong value for floor");
        }
    }
}
