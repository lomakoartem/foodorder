package ua.rd.foodorder.service;



import ua.rd.foodorder.domain.Location;


/**
 * Created by Iaroslav_Grytsaienko on 30.05.2016.
 */
public interface LocationService {

    Iterable<Location> findAll();

    Location findById(Long id);

    Location update(Location location);

    void remove(Long id);

    Location save(Location location);
}
