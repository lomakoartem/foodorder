package ua.rd.foodorder.web.dto.converter;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.web.dto.domain.VendorDto.VendorLocations;

@Component
public class VendorToDtoConverter extends AbstractConverter<List<Location>, VendorLocations> {

	@Override
	protected VendorLocations convert(List<Location> source) {
		VendorLocations locations = new VendorLocations();

		List<Long> IDs = new ArrayList<>();
		String locationsString = "";
		for (Location location : source) {
			IDs.add(location.getId());
			locationsString += location.getName() + " Fl. " + location.getFloor() + "; ";
		}
		if (locationsString != "") {
			locations.setLocations(locationsString.substring(0, locationsString.length() - 2));
		}
		locations.setLocationsId(IDs);
		return locations;
	}

}
