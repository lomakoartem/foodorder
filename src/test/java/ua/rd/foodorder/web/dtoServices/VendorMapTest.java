package ua.rd.foodorder.web.dtoServices;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.web.dto.VendorDto;

public class VendorMapTest {
	
	@Test
	public void test(){
		ModelMapper mapper = new ModelMapper();
		assertNotNull(mapper);
		mapper.addMappings(new VendorMap());
		assertNotNull(mapper);
		Vendor vendor = new Vendor();
		vendor.setName("Anton");
		List<Location> locations = new ArrayList(){{
			add(new Location("1", "1", 1, "1"));
			add(new Location("2", "1", 1, "1"));
			add(new Location("3", "1", 1, "1"));
		}};
		vendor.setLocations(locations);
		
		VendorDto vendorDto = mapper.map(vendor, VendorDto.class);
		System.out.println(vendorDto);
	}
}
