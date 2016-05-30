package ua.rd.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.repository.LocationRepository;

/**
 * Created by Iaroslav Grytsaienko on 30.05.2016.
 */
@Service
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
        return locationRepository.save(location);
    }

    @Override
    public void remove(Long id) {
        locationRepository.delete(id);
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }
}
