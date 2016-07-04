package ua.rd.foodorder.infrastructure.exceptions;

public class UnsupportedFileExtentionException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String fileExtention;

	public UnsupportedFileExtentionException(String message, String fileExtention){
		super(message);
		this.fileExtention = fileExtention;
	}

	public String getFileExtention() {
		return fileExtention;
	}
}
