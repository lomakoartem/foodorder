package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.repository.LocationRepository;
import ua.rd.foodorder.service.LocationService;

/**
 * Created by Iaroslav Grytsaienko on 30.05.2016.
 */
@Service
public class SimpleLocationService extends  AbstractService<Location, Long> implements LocationService  {

    @Override
    public Location update(Location location) {
    	
   	Location dbLocation = super.repository.findOne(location.getId());
    	
		dbLocation.setAddress(location.getAddress());
        dbLocation.setFloor(location.getFloor());
		dbLocation.setInfo(location.getInfo());
		dbLocation.setIsActive(location.getIsActive());
		dbLocation.setName(location.getName());
    	
        return super.repository.save(dbLocation);
    }

    @Override
    public void remove(Long id) {

        Location dbLocation = super.repository.findOne(id);

        dbLocation.setIsActive(false);

        super.repository.save(dbLocation);
    }
}
