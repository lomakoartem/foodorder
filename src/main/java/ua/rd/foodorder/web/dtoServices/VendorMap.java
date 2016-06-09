package ua.rd.foodorder.web.dtoServices;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.PropertyMap;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.VendorDto;

public class VendorMap extends PropertyMap<Vendor, VendorDto>{

	@Override
	protected void configure() {
		/*String  location = getLocationsString(source.getLocations());
		List<Location> locations = source.getLocations();*/
		//System.out.println(source.getName());
		map().setName(source.getName());
		
	}
	
	private List<Long> getLocationsId(List<Location> locations) {
		List<Long> IDs = new ArrayList<>();
		
		for (Location location: locations){
			IDs.add(location.getId());
		}
		return IDs;
	}

	private String getLocationsString(List<Location> locations){
		String result = "";
		for (Location location: locations){
			result += location.getName() + " Fl. " + location.getFloor() + " ";
		}
		return result;
	}
}
