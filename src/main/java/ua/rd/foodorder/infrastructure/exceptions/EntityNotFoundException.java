package ua.rd.foodorder.infrastructure.exceptions;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private long entityId;

	public EntityNotFoundException(long entityId) {
		this.entityId = entityId;
	}

	public long getEntityId() {
		return entityId;
	}
}
