package ua.rd.foodorder.exceptions;

public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long locationId;

	public EntityNotFoundException(long locationId) {
		this.locationId = locationId;
	}

	public long getLocationId() {
		return locationId;
	}

}
