package ua.rd.foodorder.web.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import ua.rd.foodorder.domain.Food;
import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.service.LocationService;
import ua.rd.foodorder.web.controller.exceptions.LocationNotFoundException;
import ua.rd.foodorder.web.controller.validators.LocationValidator;

@RestController
@RequestMapping(value = "locations")
public class LocationsController {

	private Logger logger = LoggerFactory.getLogger(LocationsController.class);

	private LocationService locationService;
	
	
	@Autowired
	private LocationValidator locationValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(locationValidator);
	}
	

	@Autowired
	public LocationsController(LocationService locationService) {
		this.locationService = locationService;
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public @ResponseBody Location locationById(@PathVariable long id) {
		Location location = locationService.findById(id);
		
		if (location == null) {
			throw new LocationNotFoundException(id);
		}
		
		return location;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Location> listLocations() {

		List<Location> result = new ArrayList<Location>();
		Iterator<Location> iterator = locationService.findAll().iterator();

		while (iterator.hasNext()) {
			result.add(iterator.next());
		}

		return result;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.PUT, consumes = "application/json")
	public Location editLocation(@Validated @RequestBody Location location) {
		return locationService.update(location);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
	public Location addLocation(@Validated @RequestBody Location location) {
		return locationService.save(location);
	}

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<Error> locationNotFound(LocationNotFoundException e) {
		long locationId = e.getLocationId();
		Error error = new Error("Location [" + locationId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}

}
