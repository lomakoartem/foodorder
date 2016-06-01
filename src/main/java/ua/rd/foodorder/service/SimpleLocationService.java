package ua.rd.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.repository.LocationRepository;

/**
 * Created by Iaroslav Grytsaienko on 30.05.2016.
 */
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
        return locationRepository.findOne(id);
    }

    @Override
    public Location update(Location location) {
    	
   	Location dbLocation = locationRepository.findOne(location.getId());
    	
		dbLocation.setAddress(location.getAddress());
		dbLocation.setInfo(location.getInfo());
		dbLocation.setActive(location.isActive());
		dbLocation.setName(location.getName());
    	
        return locationRepository.save(dbLocation);
    }

    @Override
    public void remove(Long id) {

        Location dbLocation = locationRepository.findOne(id);

        dbLocation.setActive(false);

        locationRepository.save(dbLocation);
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
