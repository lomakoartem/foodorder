package ua.rd.foodorder.infrastructure.exceptions;

public class FileParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileParsingException(String message){
		super(message);
	}
	
	public FileParsingException(Throwable throwable) {
		super(throwable);
	}
	
}
