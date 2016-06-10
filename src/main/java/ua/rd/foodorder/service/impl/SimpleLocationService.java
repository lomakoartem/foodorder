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

	@Autowired
	LocationRepository locationRepository;

	@Override
	public Iterable<Location> findAll() {
		return locationRepository.findAll();
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

		Location dbLocation = locationRepository.findOne(location.getId());

		 if (dbLocation == null) {
	            throw new EntityNotFoundException(location.getId());
	        }
		
		dbLocation.setAddress(location.getAddress());
		dbLocation.setInfo(location.getInfo());
		dbLocation.setIsActive(location.getIsActive());
		dbLocation.setName(location.getName());

		return locationRepository.save(dbLocation);
	}

	@Override
	public void remove(Long id) {

		Location dbLocation = locationRepository.findOne(id);
		
        dbLocation.setIsActive(false);

		locationRepository.save(dbLocation);
	}

	@Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
