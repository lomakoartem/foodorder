package ua.rd.foodorder.web.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "locations")
public class LocationsController {
	
	private Logger logger = LoggerFactory.getLogger(LocationsController.class);

	@RequestMapping(value ="/list", method = RequestMethod.GET)
	public List listLocations(){
		return null;
	}
	
	@RequestMapping(value ="/edit/{id}", method = RequestMethod.POST)
	public Object editLocation(@PathVariable Long id){
		
		return null;
	}
	

	@RequestMapping(value ="/add", method = RequestMethod.POST)
	public Object editLocation(){
		
		return null;
	}

	
	
}
