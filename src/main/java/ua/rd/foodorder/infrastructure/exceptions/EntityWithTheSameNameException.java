package ua.rd.foodorder.infrastructure.exceptions;

public class EntityWithTheSameNameException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityWithTheSameNameException(String message){
		super(message);
	}
}
