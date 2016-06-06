package ua.rd.foodorder.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.service.LocationService;

@Service
public class LocationFacade {

	@Autowired
	private LocationService locationService;
	
	public Location findByIdAndCheck(Long id){
		Location location = locationService.findById(id);

        if (location == null) {
            throw new EntityNotFoundException(id);
        }

        return location;
	}
	
	public void remove(Long id){
		locationService.remove(id);
	}
	
	public Iterable<Location> getLocationList(){
        return locationService.findAll();
	}
	
	public Location editLocation(Long id, Location location){
		Location dbLocation = locationService.findById(id);

        if (dbLocation == null) {
            throw new EntityNotFoundException(id);
        }

        return locationService.update(location);
	}
	
	public Location addLocation(Location location){
		return locationService.save(location);
	}
}
