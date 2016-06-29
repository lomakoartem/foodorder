package ua.rd.foodorder.infrastructure.exceptions;

public class WrongFileContentException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public WrongFileContentException(String message){
		super(message);
	}
	
}
