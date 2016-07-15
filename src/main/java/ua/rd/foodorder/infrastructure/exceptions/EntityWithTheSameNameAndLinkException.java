package ua.rd.foodorder.infrastructure.exceptions;

public class EntityWithTheSameNameAndLinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityWithTheSameNameAndLinkException(String message){
		super(message);
	}

}
