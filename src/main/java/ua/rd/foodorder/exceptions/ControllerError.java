package ua.rd.foodorder.exceptions;

public class ControllerError {
	private int code;
	private String message;

	public ControllerError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}