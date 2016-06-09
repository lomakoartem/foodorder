package ua.rd.foodorder.service;


import ua.rd.foodorder.domain.Location;


public interface LocationService {
	Iterable<Location> findAll();

    Location findById(Long id);

    Location update(Location location);

    void remove(Long id);

    Location save(Location location);
}
