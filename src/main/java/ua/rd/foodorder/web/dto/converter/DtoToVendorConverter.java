package ua.rd.foodorder.web.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.service.LocationService;
import ua.rd.foodorder.web.dto.domain.VendorDto.VendorLocations;


@Component
public class DtoToVendorConverter extends AbstractConverter<VendorLocations, List<Location>>{

	private LocationService locationService;
		
	public LocationService getLocationService() {
		return locationService;
	}

	@Autowired
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}



	@Override
	protected List<Location> convert(VendorLocations source) {
		List<Long> locationsIDs = source.getLocationsId();
		List<Location> locations = new ArrayList<>();
		
		for(Long id : locationsIDs){
			locations.add(locationService.findById(id));
		}
		
		return locations;
	}

}
