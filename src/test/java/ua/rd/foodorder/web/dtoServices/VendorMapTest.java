package ua.rd.foodorder.web.dtoServices;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.converter.DtoToVendorConverter;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.domain.VendorDto.VendorLocations;

public class VendorMapTest {
	
	@Test
	public void test(){
		ModelMapper mapper = new ModelMapper();
		Converter<List<Location>, VendorLocations> converter = new AbstractConverter<List<Location>,VendorLocations>() {

			@Override
			protected VendorLocations convert(List<Location> source) {
				
				VendorLocations locations = new VendorLocations();
				
				List<Long> IDs = new ArrayList<>();
				String locationsString = "";
				for (Location location: source){
					IDs.add(location.getId());
					locationsString += location.getName() + " Fl. " + location.getFloor() + " ";
				}
				locations.setLocations(locationsString);
				locations.setLocationsId(IDs);
				return locations;
			}
			
		};
		
		DtoToVendorConverter converter1 = new DtoToVendorConverter();
		
		assertNotNull(mapper);
		mapper.addConverter(converter);
		mapper.addConverter(converter1);
		assertNotNull(mapper);
		Vendor vendor = new Vendor();
		vendor.setName("Anton");
		vendor.setActive(true);
		vendor.setEmail("asdsadasd");
		vendor.setPhone("423423");
		vendor.setId(1l);
		List<Location> locations = new ArrayList(){{
			add(new Location(1l, "loc1", "1", 1, "1"));
			add(new Location(2l, "loc2", "1", 1, "1"));
			add(new Location(3l, "loc3", "1", 1, "1"));
		}};
		vendor.setLocations(locations);
		
		VendorDto vendorDto = mapper.map(vendor, VendorDto.class);
		System.out.println(vendorDto);
		vendor = mapper.map(vendorDto, Vendor.class);
		System.out.println(vendor.getLocations().get(0));
	}
}
