package ua.rd.foodorder.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.service.LocationService;


@ContextConfiguration(locations = { "classpath:/ApplicationContext.xml", "classpath:/repositoryH2Context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class LocationControllerTest {

	private MockMvc mockMvc;

	@Mock
	private LocationService locationService;

	@InjectMocks
	private LocationsController locationController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		locationController = new LocationsController(locationService);
		mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();

	}

	@Test
	public void findAllLocationFoundShouldReturnFoundLocations() throws Exception {
		when(locationService.findAll()).thenReturn(getLocationList());
		mockMvc.perform(get("/api/locations/list")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].name", is("K14")))
				.andExpect(jsonPath("$[0].info", is("K14"))).andExpect(jsonPath("$[0].address", is("K14")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].name", is("K18")))
				.andExpect(jsonPath("$[1].info", is("K18"))).andExpect(jsonPath("$[1].address", is("K18")))
				.andExpect(jsonPath("$[2].id", is(3))).andExpect(jsonPath("$[2].name", is("F30")))
				.andExpect(jsonPath("$[2].info", is("F30"))).andExpect(jsonPath("$[2].address", is("F30")))
				.andDo(print());
		verify(locationService).findAll();
	}

	@Test
	public void findByIdLocationFoundShouldReturnFoundLocation() throws Exception {
		when(locationService.findById(1l)).thenReturn(getLocation());
		mockMvc.perform(get("/api/locations/list/{id}", 1l)).andExpect(status().isFound())
				.andExpect(jsonPath("$id", is(1))).andExpect(jsonPath("$name", is("K14")))
				.andExpect(jsonPath("$info", is("K14"))).andExpect(jsonPath("$address", is("K14")));
		verify(locationService).findById(1l);
		verifyNoMoreInteractions(locationService);
	}

	@Test
	public void findByIdLocationNotFoundShouldReturnHttpStatus404() throws Exception {
		when(locationService.findById(1l)).thenThrow(new EntityNotFoundException(1l));
		mockMvc.perform(get("/api/locations/list/{id}", 1l)).andExpect(status().isNotFound());
		verify(locationService).findById(1l);
		verifyNoMoreInteractions(locationService);
	}

	@Test
	public void editLocationFoundLocationShouldUpdateFoundLocation() throws Exception{
		Location location = getLocation();
		byte [] locationJson = convertIntoJson(location);
		when(locationService.update(location)).thenReturn(location);
		mockMvc.perform(put("/api/locations/list/{id}", 1l)
				.contentType(MediaType.APPLICATION_JSON)
                .content(locationJson))
				.andExpect(jsonPath("$id", is(1)))
				.andExpect(jsonPath("$name", is("K14")))
				.andExpect(jsonPath("$info", is("K14")))
				.andExpect(jsonPath("$address", is("K14")));
		verify(locationService).update(location);
		verifyNoMoreInteractions(locationService);
	}
	
	@Test
	public void editLocationNotFoundLocationShouldReturnHttpStatus404() throws Exception{
		Location location = getLocation();
		byte [] locationJson = convertIntoJson(location);
		when(locationService.update(location)).thenThrow(new EntityNotFoundException(1l));
		mockMvc.perform(put("/api/locations/list/{id}", 1l)
			.contentType(MediaType.APPLICATION_JSON)
			.content(locationJson))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void addLocationShouldSaveLocation() throws Exception{
		Location location = getLocation();
		byte [] locationJson = convertIntoJson(location);
		when(locationService.save(location)).thenReturn(location);
		mockMvc.perform(post("/api/locations/list")
			.contentType(MediaType.APPLICATION_JSON)
			.content(locationJson))
			.andExpect(jsonPath("$id", is(1)))
			.andExpect(jsonPath("$name", is("K14")))
			.andExpect(jsonPath("$info", is("K14")))
			.andExpect(jsonPath("$address", is("K14")));
		verify(locationService).save(location);
	}

	private Iterable<Location> getLocationList() {
		List<Location> locations = new ArrayList<>();
		Location location1 = new Location("K14", "K14",5, "K14");
		location1.setId(1l);
		Location location2 = new Location("K18", "K18",5, "K18");
		location2.setId(2l);
		Location location3 = new Location("F30", "F30",5, "F30");
		location3.setId(3l);

		locations.add(location1);
		locations.add(location2);
		locations.add(location3);

		return new Iterable<Location>() {
			
			@Override
			public Iterator<Location> iterator() {
				return locations.iterator();
			}
		};
	}

	private Location getLocation() {
		Location location = new Location("K14", "K14",5, "K14");
		location.setId(1l);
		return location;
	}

	private byte[] convertIntoJson(Location e) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(e);
	}
}
