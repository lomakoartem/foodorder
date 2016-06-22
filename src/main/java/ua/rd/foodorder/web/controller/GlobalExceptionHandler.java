package ua.rd.foodorder.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.rd.foodorder.infrastructure.exceptions.ControllerError;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ControllerError> locationNotFound(EntityNotFoundException e) {
		long entityId = e.getEntityId();
		ControllerError error = new ControllerError(1, "Entity [" + entityId + "] not found");
		return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityFormatException.class)
	public ResponseEntity<ControllerError> locationIncorrectFormat(EntityFormatException e) {
		ControllerError error = new ControllerError(2, "Format of object incorrect");
		return new ResponseEntity<ControllerError>(error, HttpStatus.BAD_REQUEST);
	}
}
