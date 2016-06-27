package ua.rd.foodorder.web.dto.converters.vendor;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.AbstractConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.service.LocationService;
import ua.rd.foodorder.web.dto.domain.VendorDTO.VendorLocations;

@Component
public class VendorLocationsToLocationsConverter extends AbstractConverter<VendorLocations, List<Location>> {

	private LocationService locationService;

	@Override
	protected List<Location> convert(VendorLocations source) {
		if (source == null) {
			return new ArrayList<>();
		}
		List<Long> locationsIDs = source.getLocationsId();
		List<Location> locations = new ArrayList<>();

		for (Long id : locationsIDs) {
			locations.add(locationService.findById(id));
		}
		return locations;
	}

	public LocationService getLocationService() {
		return locationService;
	}

	@Autowired
	public void setLocationService(LocationService locationService) {
		this.locationService = locationService;
	}
}
