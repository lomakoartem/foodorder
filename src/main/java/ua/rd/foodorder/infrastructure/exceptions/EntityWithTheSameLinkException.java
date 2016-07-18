package ua.rd.foodorder.infrastructure.exceptions;

public class EntityWithTheSameLinkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityWithTheSameLinkException(String message){
		super(message);
	}
}
