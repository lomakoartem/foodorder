package ua.rd.foodorder.web.dtoServices;

import java.util.List;

import org.modelmapper.PropertyMap;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.VendorDto;

public class VendorMap extends PropertyMap<Vendor, VendorDto>{

	@Override
	protected void configure() {
		//String  location = getLocationsString(source.getLocations());
		map().setLocations("fdfsfsdfsdfsdfds");
		map().setName(source.getName());
	}
	
	private String getLocationsString(List<Location> locations){
		StringBuilder result = new StringBuilder();
		for (Location location: locations){
			result.append(location.getName() + " Fl. " + location.getFloor());
		}
		return result.toString();
	}
}
