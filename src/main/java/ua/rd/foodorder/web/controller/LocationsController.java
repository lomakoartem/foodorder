package ua.rd.foodorder.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.service.facade.LocationFacade;
import ua.rd.foodorder.web.controller.validators.LocationValidator;

@RestController
@RequestMapping(value = "/api/locations")
public class LocationsController {

    private Logger logger = LoggerFactory.getLogger(LocationsController.class);

    private LocationFacade locationFacade;
    
	private LocationValidator locationValidator;
	
	public LocationValidator getLocationValidator() {
		return locationValidator;
	}
	
	@Autowired
	public void setLocationValidator(LocationValidator locationValidator) {
		this.locationValidator = locationValidator;
	}
    
    @Autowired
    public LocationsController(LocationFacade locationFacade) {
		this.locationFacade = locationFacade;
	}
	
	@InitBinder
	private void initBinder(WebDataBinder binder){
		binder.addValidators(locationValidator);
	}
    
    
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Location locationById(@PathVariable Long id) {
        return locationFacade.findByIdAndCheck(id);
    }


    @RequestMapping(value = "/list/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
        locationFacade.remove(id);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Iterable<Location> listLocations() {
        return locationFacade.getLocationList();
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Location editLocation(@PathVariable Long id, @Validated @RequestBody Location location, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        return locationFacade.editLocation(id, location);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Location> addLocation(@Validated @RequestBody Location location, BindingResult bindingResult,  UriComponentsBuilder ucBuilder) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        Location newLocation = locationFacade.addLocation(location);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/locations/list/{id}").buildAndExpand(newLocation.getId()).toUri());
  
        return new ResponseEntity<Location>(newLocation, headers, HttpStatus.CREATED);
    }

}
