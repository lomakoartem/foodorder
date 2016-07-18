package ua.rd.foodorder.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.rd.foodorder.infrastructure.exceptions.ControllerError;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.infrastructure.exceptions.EntityWithTheSameLinkException;
import ua.rd.foodorder.infrastructure.exceptions.EntityWithTheSameNameAndLinkException;
import ua.rd.foodorder.infrastructure.exceptions.EntityWithTheSameNameException;
import ua.rd.foodorder.infrastructure.exceptions.UnsupportedFileExtentionException;
import ua.rd.foodorder.infrastructure.exceptions.FileParsingException;
import ua.rd.foodorder.infrastructure.exceptions.SearchNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ControllerError> entityNotFound(EntityNotFoundException e) {
		long entityId = e.getEntityId();
		ControllerError error = new ControllerError(1, "Entity [" + entityId + "] not found");
		return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityFormatException.class)
	public ResponseEntity<ControllerError> entityIncorrectFormat(EntityFormatException e) {
		ControllerError error = new ControllerError(2, "Format of object incorrect");
		return new ResponseEntity<ControllerError>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnsupportedFileExtentionException.class)
	public ResponseEntity<ControllerError> unsupportedFileExtention(UnsupportedFileExtentionException e) {
		String fileExtention = e.getFileExtention();
		ControllerError error = new ControllerError(3, "Received file with unsupported extention: " + fileExtention + ".");
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FileParsingException.class)
	public ResponseEntity<ControllerError> unsupportedFileExtention(FileParsingException e) {
		ControllerError error = new ControllerError(4, "File parsing exception: " + e.getLocalizedMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(SearchNotFoundException.class)
	public ResponseEntity<ControllerError> incorrectSearch(SearchNotFoundException e) {
		ControllerError error = new ControllerError(5, "Search was not succesful");
		return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EntityWithTheSameNameException.class)
	public ResponseEntity<ControllerError> entityWithTheSameNameException(EntityWithTheSameNameException e) {
		ControllerError error = new ControllerError(6, "The duplicate name exception:" + e.getLocalizedMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EntityWithTheSameLinkException.class)
	public ResponseEntity<ControllerError> entityWithTheSameLinkException(EntityWithTheSameLinkException e) {
		ControllerError error = new ControllerError(7, "The duplicate link exception: " + e.getLocalizedMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EntityWithTheSameNameAndLinkException.class)
	public ResponseEntity<ControllerError> entityWithTheSameNameAndLinkException(EntityWithTheSameNameAndLinkException e) {
		ControllerError error = new ControllerError(8, "The duplicate name and link exception: " + e.getLocalizedMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
