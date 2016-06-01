package ua.rd.foodorder.web.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.service.LocationService;
import ua.rd.foodorder.web.controller.exceptions.ControllerError;
import ua.rd.foodorder.web.controller.exceptions.LocationFormatException;
import ua.rd.foodorder.web.controller.exceptions.LocationNotFoundException;
import ua.rd.foodorder.web.controller.validators.LocationValidator;

@RestController
@RequestMapping(value = "/api/locations")
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
    @ResponseStatus(HttpStatus.FOUND)
    public Location locationById(@PathVariable Long id) {
        Location location = locationService.findById(id);

        if (location == null) {
            throw new LocationNotFoundException(id);
        }

        return location;
    }


    @RequestMapping(value = "/list/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
        locationService.remove(id);
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

    @RequestMapping(value = "/list/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Location editLocation(@PathVariable Long id, @Validated @RequestBody Location location, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new LocationFormatException();
        }

        Location dbLocation = locationService.findById(id);

        if (dbLocation == null) {
            throw new LocationNotFoundException(id);
        }

        return locationService.update(location);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
    public Location addLocation(@Validated @RequestBody Location location, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new LocationFormatException();
        }

        return locationService.save(location);
    }

    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ControllerError> locationNotFound(LocationNotFoundException e) {
        long locationId = e.getLocationId();
        ControllerError error = new ControllerError(1, "Location [" + locationId + "] not found");
        return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(LocationFormatException.class)
    public ResponseEntity<ControllerError> locationIncorrectFormat(LocationFormatException e) {
        ControllerError error = new ControllerError(2, "Format of location object incorrect");
        return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_ACCEPTABLE);
    }

}
