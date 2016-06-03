package ua.rd.foodorder.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.repository.LocationRepository;

/*@ContextConfiguration(locations = { "classpath:/ApplicationContext.xml", "classpath:/repositoryH2Context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)*/
public class LocationControllerIT {

	@Test
	public void test(){
		
	}
	
	/*@Autowired
	private LocationsController locationController;

	@Autowired
	private LocationRepository locationRepository;

	private MockMvc mockMvc;

	private List<Location> locations = new ArrayList<>();

	@Before
	public void setUpDB() {
		Location locationOne = new Location("K14", "K14", "K14");
		Location locationTwo = new Location("K18", "K18", "K18");
		Location locationThree = new Location("F30", "F30", "F30");
		locationOne.setId(1L);
		locationTwo.setId(2L);
		locationThree.setId(3L);
		locations.add(locationOne);
		locations.add(locationTwo);
		locations.add(locationThree);
		locationRepository.save(locations);
	}

	@Before
	public void setUpMockMvc() {
		mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
	}

	@After
	public void clearDB() {
		locationRepository.deleteAll();
		locations.clear();
	}

	@Test
	public void findAllLocationFoundShouldReturnFoundLocations() throws Exception {
		Location[] savedLocations = getSavedLocations();
		Location locOne = savedLocations[0];
		Location locTwo = savedLocations[1];
		Location locThree = savedLocations[2];
		mockMvc.perform(get("/api/locations/list")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(locOne.getId().intValue()))).andExpect(jsonPath("$[0].name", is(locOne.getName())))
				.andExpect(jsonPath("$[0].info", is(locOne.getInfo()))).andExpect(jsonPath("$[0].address", is(locOne.getAddress())))
				.andExpect(jsonPath("$[1].id", is(locTwo.getId().intValue()))).andExpect(jsonPath("$[1].name", is(locTwo.getName())))
				.andExpect(jsonPath("$[1].info", is(locTwo.getInfo()))).andExpect(jsonPath("$[1].address", is(locTwo.getAddress())))
				.andExpect(jsonPath("$[2].id", is(locThree.getId().intValue()))).andExpect(jsonPath("$[2].name", is(locThree.getName())))
				.andExpect(jsonPath("$[2].info", is(locThree.getInfo()))).andExpect(jsonPath("$[2].address", is(locThree.getAddress())))
				.andDo(print());
	}

	@Test
	public void findByIdLocationFoundShouldReturnFoundLocation() throws Exception {
		Location[] savedLocations = getSavedLocations();
		Location firstSavedLocation = savedLocations[0];
		mockMvc.perform(get("/api/locations/list/{id}", firstSavedLocation.getId())).andExpect(status().isFound())
				.andExpect(jsonPath("$id", is(firstSavedLocation.getId().intValue()))).andExpect(jsonPath("$name", is(firstSavedLocation.getName())))
				.andExpect(jsonPath("$info", is(firstSavedLocation.getInfo()))).andExpect(jsonPath("$address", is(firstSavedLocation.getAddress())));
	}

	@Test
	public void findByIdLocationNotFoundShouldReturnHttpStatus404() throws Exception {
		Long newId = getNextInsertedElementId();
		mockMvc.perform(get("/api/locations/list/{id}", newId)).andExpect(status().isNotFound());
	}

	@Test
	public void editLocationFoundLocationShouldUpdateFoundLocation() throws Exception {
		Location existingLocation = getSavedLocations()[1];
		Location location = getLocation(existingLocation.getId());
		byte[] locationJson = convertIntoJson(location);
		mockMvc.perform(
				put("/api/locations/list/{id}", location.getId()).contentType(MediaType.APPLICATION_JSON).content(locationJson))
				.andExpect(jsonPath("$id", is(location.getId().intValue()))).andExpect(jsonPath("$name", is(location.getName())))
				.andExpect(jsonPath("$info", is(location.getInfo()))).andExpect(jsonPath("$address", is(location.getAddress())));
	}

	@Test
	public void editLocationNotFoundLocationShouldReturnHttpStatus404() throws Exception {
		Long newId = getNextInsertedElementId();
		Location location = getLocation(newId);
		byte[] locationJson = convertIntoJson(location);
		mockMvc.perform(put("/api/locations/list/{id}", newId).contentType(MediaType.APPLICATION_JSON)
				.content(locationJson)).andExpect(status().isNotFound());
	}

	@Test
	public void addLocationShouldSaveLocation() throws Exception {
		Long newId = getNextInsertedElementId();
		Location location = getLocation(newId);
		byte[] locationJson = convertIntoJson(location);
		mockMvc.perform(post("/api/locations/list").contentType(MediaType.APPLICATION_JSON).content(locationJson))
				.andExpect(jsonPath("$id", is(newId.intValue()))).andExpect(jsonPath("$name", is(location.getName())))
				.andExpect(jsonPath("$info", is(location.getInfo()))).andExpect(jsonPath("$address", is(location.getAddress())));
	}

	private Long getNextInsertedElementId() {
		Location[] savedLocations = getSavedLocations();
		Long newId = savedLocations[savedLocations.length - 1].getId() + 1;
		return newId;
	}

	private Location getLocation(Long id) {
		Location location1 = new Location("K14", "K14", "K14");
		location1.setId(id);
		return location1;
	}

	private byte[] convertIntoJson(Location e) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(e);
	}

	private Location[] getSavedLocations() {
		Iterable<Location> savedLocationsIterable = locationRepository.findAll();
		Location[] savedLocationsArray = new Location[3];
		int i = 0;
		for (Location location : savedLocationsIterable) {
			System.out.println("i: " + i + "; location id: " + location.getId());
			savedLocationsArray[i++] = location;
		}
		System.out.println(savedLocationsArray);
		return savedLocationsArray;

	}*/
}


