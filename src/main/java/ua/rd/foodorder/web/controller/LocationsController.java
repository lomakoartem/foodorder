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
import ua.rd.foodorder.service.LocationService;
import ua.rd.foodorder.web.controller.validators.LocationValidator;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/locations")
public class LocationsController {

    private static final Logger LOG = LoggerFactory.getLogger(LocationsController.class);

    private LocationService locationService;

    private LocationValidator locationValidator;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Location getLocationById(@PathVariable Long id) {
        return locationService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
        locationService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Location> getAllLocations() {
        return locationService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Location editLocation(@PathVariable Long id, @Validated @RequestBody Location location, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        return locationService.update(location);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Location> addLocation(@Validated @RequestBody Location location, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        Location newLocation = locationService.save(location);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/locations/{id}").buildAndExpand(newLocation.getId()).toUri());

        return new ResponseEntity<Location>(newLocation, headers, HttpStatus.CREATED);
    }

    public LocationValidator getLocationValidator() {
        return locationValidator;
    }

    @Autowired
    public void setLocationValidator(LocationValidator locationValidator) {
        this.locationValidator = locationValidator;
    }

    @Autowired
    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(locationValidator);
    }
}
