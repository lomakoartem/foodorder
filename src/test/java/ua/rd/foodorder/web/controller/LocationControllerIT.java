package ua.rd.foodorder.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ua.rd.foodorder.domain.Location;

@ContextConfiguration(locations = { "classpath:/ApplicationContext.xml", "classpath:/repositoryH2Context.xml",
		"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class LocationControllerIT {

	private JdbcTemplate jdbcTemplate;

	private MockMvc mockMvc;

	@Autowired
	private LocationsController locationsController;

	@Autowired
	public void setDataSource(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
	}

	@Before
	public void setUpMockMvc() {
		mockMvc = MockMvcBuilders.standaloneSetup(locationsController).build();
	}

	private int insertLocationToDB(Location location) {
		String sqlInsert = "INSERT INTO locations "
				+ "(location_id, location_name, location_address, location_info, location_isactive, location_floor) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		int updateResult = jdbcTemplate.update((Connection con) -> {
			PreparedStatement ps = con.prepareStatement(sqlInsert);
			ps.setLong(1, location.getId());
			ps.setString(2, location.getName());
			ps.setString(3, location.getAddress());
			ps.setString(4, location.getInfo());
			ps.setBoolean(5, true);
			ps.setShort(6, location.getFloor().shortValue());
			return ps;
		});
		return updateResult;
	}

	private Location createLocation(Long id, String name, String address, Integer floor, String info, Boolean isActive) {
		Location location = new Location(name, address, floor, info);
		location.setId(id);
		location.setActive(isActive);
		return location;
	}

	private List<Location> getInsertedLocations() {
		Location locationOne = createLocation(1L, "K14", "K14", 4, "K14", true);
		Location locationTwo = createLocation(2L, "K18", "K18", 5, "K18", true);
		Location locationThree = createLocation(3L, "F30", "F30", 6, "F30", true);
		insertLocationToDB(locationOne);
		insertLocationToDB(locationTwo);
		insertLocationToDB(locationThree);
		List<Location> insertedLocations = new ArrayList<>();
		insertedLocations.add(locationOne);
		insertedLocations.add(locationTwo);
		insertedLocations.add(locationThree);
		return insertedLocations;
	}

	private Location createLocationAndInsertToDB(Long id, String name, String address, Integer floor, String info, Boolean isActive) {
		Location location = createLocation(id, name, address, floor, info, isActive);
		insertLocationToDB(location);
		return location;
	}

	private byte[] convertIntoJson(Location e) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(e);
	}

	@Test
	public void findAllLocationFoundShouldReturnFoundLocations() throws Exception {
		List<Location> insertedLocations = getInsertedLocations();
		int currentLocationInJsonArrayId = 0;
		for (Location location : insertedLocations) {
			mockMvc.perform(get("/api/locations/list")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
					.andExpect(jsonPath("$[" + currentLocationInJsonArrayId + "].id", is(location.getId().intValue())))
					.andExpect(jsonPath("$[" + currentLocationInJsonArrayId + "].name", is(location.getName())))
					.andExpect(jsonPath("$[" + currentLocationInJsonArrayId + "].info", is(location.getInfo())))
					.andExpect(jsonPath("$[" + currentLocationInJsonArrayId + "].address", is(location.getAddress())));
			++currentLocationInJsonArrayId;
		}
	}

	@Test
	public void findByIdLocationFoundShouldReturnFoundLocation() throws Exception {
		Location location = createLocationAndInsertToDB(1L, "K14", "K14", 4, "K14", true);
		mockMvc.perform(get("/api/locations/list/{id}", location.getId()))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$id", is(location.getId().intValue())))
				.andExpect(jsonPath("$name", is(location.getName())))
				.andExpect(jsonPath("$info", is(location.getInfo())))
				.andExpect(jsonPath("$address", is(location.getAddress())));
	}

	@Test
	public void findByIdLocationNotFoundShouldReturnHttpStatus404() throws Exception {
		mockMvc.perform(get("/api/locations/list/{id}", 1l))
				.andExpect(status().isNotFound());
	}

	@Test
	public void editLocationFoundLocationShouldUpdateFoundLocation() throws Exception {
		Location location = createLocationAndInsertToDB(1L, "K14", "K14", 5, "K14", true);
		Location updatedLocation = createLocation(1L, "K15", "K15", 6, "K15", true);
		byte[] updatedLocationJson = convertIntoJson(updatedLocation);
		mockMvc.perform(put("/api/locations/list/{id}", location.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(updatedLocationJson)).andExpect(jsonPath("$id", is(updatedLocation.getId().intValue())))
				.andExpect(jsonPath("$name", is(updatedLocation.getName())))
				.andExpect(jsonPath("$info", is(updatedLocation.getInfo())))
				.andExpect(jsonPath("$address", is(updatedLocation.getAddress())));
	}

	@Test
	public void editLocationNotFoundLocationShouldReturnHttpStatus404() throws Exception {
		Location location = createLocation(1L, "K15", "K15", 3, "K15", true);
		byte[] locationJson = convertIntoJson(location);
		mockMvc.perform(put("/api/locations/list/{id}", location.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(locationJson))
				.andExpect(status().isNotFound());
	}

	@Test
	public void addLocationShouldSaveLocation() throws Exception {
		Location location = createLocation(1L, "K14", "K14", 2, "K14", true);
		byte[] locationJson = convertIntoJson(location);
		mockMvc.perform(post("/api/locations/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(locationJson))
				.andExpect(jsonPath("$id", is(location.getId().intValue())))
				.andExpect(jsonPath("$name", is(location.getName())))
				.andExpect(jsonPath("$info", is(location.getInfo())))
				.andExpect(jsonPath("$address", is(location.getAddress())));
	}

}
