package ua.rd.foodorder.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.web.controller.exceptions.LocationNotFoundException;

@Service
public class LocationFacade {

	@Autowired
	private LocationService locationService;
	
	public Location findByIdAndCheck(Long id){
		Location location = locationService.findById(id);

        if (location == null) {
            throw new LocationNotFoundException(id);
        }

        return location;
	}
	
	public void remove(Long id){
		locationService.remove(id);
	}
	
	public List<Location> getLocationList(){
		List<Location> result = new ArrayList<Location>();
        Iterator<Location> iterator = locationService.findAll().iterator();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
	}
	
	public Location editLocation(Long id, Location location){
		Location dbLocation = locationService.findById(id);

        if (dbLocation == null) {
            throw new LocationNotFoundException(id);
        }

        return locationService.update(location);
	}
	
	public Location addLocation(Location location){
		return locationService.save(location);
	}
}
