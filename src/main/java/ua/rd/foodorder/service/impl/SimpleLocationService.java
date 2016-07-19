package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.LocationRepository;
import ua.rd.foodorder.service.LocationService;

@Service
@Transactional
public class SimpleLocationService implements LocationService {

	private final LocationRepository locationRepository;

	@Autowired
	public SimpleLocationService(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}

	@Override
	public Iterable<Location> findAll() {
		return locationRepository.findAll();
	}

	@Override
	public Iterable<Location> findAllActive() {
		return locationRepository.findByIsActiveTrue();
	}

	@Override
	public Location findById(Long id) {
		Location location = locationRepository.findOne(id);

		if (location == null) {
			throw new EntityNotFoundException(id);
		}

		return location;
	}

	@Override
	public Location update(Location location) {
		Location dbLocation = findById(location.getId());
		checkRemovingVendorReferenceToNotActiveLocations(location, dbLocation);
		return locationRepository.save(location);
	}

	/**
	 * Checks location before and after update to ensure need in removing vendor
	 * reference to inactive locations.
	 * <p>
	 * If found that location after being active becomes inactive - it is
	 * removed from all vendors.
	 * 
	 * @param location
	 *            location after update
	 * @param dbLocation
	 *            location before update
	 */
	private void checkRemovingVendorReferenceToNotActiveLocations(Location location, Location dbLocation) {
		boolean activeAfter = location.getActive();
		boolean activeBefore = dbLocation.getActive();

		if (activeBefore && !activeAfter) {
			removeVendorReferenceToLocation(location.getId());
		}
	}

	private void removeVendorReferenceToLocation(Long id) {
		locationRepository.removeVendorReferenceToLocation(id);
	}

	@Override
	public void remove(Long id) {
		Location dbLocation = findById(id);
		dbLocation.setActive(false);
		locationRepository.save(dbLocation);
	}

	@Override
	public Location save(Location location) {
		return locationRepository.save(location);
	}
}
