package ua.rd.foodorder.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.any;
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
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
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

import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.service.VendorDtoService;

@ContextConfiguration(locations = { "classpath:/ApplicationContext.xml", "classpath:/repositoryH2Context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class VendorControllerTest {

	private MockMvc mockMvc;

	@Mock
	private VendorDtoService vendorDtoService;

	@InjectMocks
	private VendorsController vendorsController;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		vendorsController = new VendorsController(vendorDtoService);
		mockMvc = MockMvcBuilders.standaloneSetup(vendorsController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
	}

	@Test
	public void findByIdVendorFoundShouldReturnFoundVendor() throws Exception {
		when(vendorDtoService.findById(1l)).thenReturn(getVendorDto());
		mockMvc.perform(get("/api/vendors/{id}", 1l))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$id", is(1)))
				.andExpect(jsonPath("$name", is("DominoPizza")))
				.andExpect(jsonPath("$email", is("domino.pizza@com.ua")))
				.andExpect(jsonPath("$additionalInfo", is("1234")))
				.andExpect(jsonPath("$active", is(true)))
				.andExpect(jsonPath("$locations.locations", is("k14 fl1; k14 fl2; k14 fl3")))
				.andExpect(jsonPath("$locations.locationsId", contains(1, 2, 3)));
		verify(vendorDtoService).findById(1l);
		verifyNoMoreInteractions(vendorDtoService);
	}

	@Test
	public void findByIdVendorNotFoundShouldReturnHttpStatus404() throws Exception {
		when(vendorDtoService.findById(1l)).thenThrow(new EntityNotFoundException(1l));
		mockMvc.perform(get("/api/vendors/{id}", 1l)).andExpect(status().isNotFound());
		verify(vendorDtoService).findById(1l);
		verifyNoMoreInteractions(vendorDtoService);
	}

	@Test
	public void addVendorDtoShouldSaveVendorDto() throws Exception {
		VendorDto vendorDto = getVendorDto();
		byte[] vendorDtoJson = convertIntoJson(vendorDto);
		when(vendorDtoService.save(Matchers.<VendorDto> any()))
				.thenReturn(vendorDto);
		mockMvc.perform(post("/api/vendors")
				.contentType(MediaType.APPLICATION_JSON)
				.content(vendorDtoJson))
				.andExpect(jsonPath("$id", is(1)))
				.andExpect(jsonPath("$name", is("DominoPizza")))
				.andExpect(jsonPath("$email", is("domino.pizza@com.ua")))
				.andExpect(jsonPath("$additionalInfo", is("1234")))
				.andExpect(jsonPath("$active", is(true)))
				.andExpect(jsonPath("$locations.locations", is("k14 fl1; k14 fl2; k14 fl3")))
				.andExpect(jsonPath("$locations.locationsId", contains(1, 2, 3)));
		verify(vendorDtoService).save(Matchers.<VendorDto> any());
	}
	
	
	@Test
	public void findAllVendorDtoShouldReturnVendorDto() throws Exception {
		when(vendorDtoService.findAll()).thenReturn(getVendorDtoList());
		mockMvc.perform(get("/api/vendors"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("DominoPizza")))
				.andExpect(jsonPath("$[0].email", is("domino.pizza@com.ua")))
				.andExpect(jsonPath("$[0].additionalInfo", is("1234")))
				.andExpect(jsonPath("$[0].active", is(true)))
				.andExpect(jsonPath("$[0].locations.locations", is("k14 fl1; k14 fl2; k14 fl3")))
				.andExpect(jsonPath("$[0].locations.locationsId", contains(1, 2, 3)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("DominoPizza1")))
				.andExpect(jsonPath("$[1].email", is("domino.pizza@com.ua1")))
				.andExpect(jsonPath("$[1].additionalInfo", is("12345")))
				.andExpect(jsonPath("$[1].active", is(true)))
				.andExpect(jsonPath("$[1].locations.locations", is("k14 fl3; k14 fl4; k14 fl5")))
				.andExpect(jsonPath("$[1].locations.locationsId", contains(3, 4, 5)))
				.andExpect(jsonPath("$[2].id", is(3)))
				.andExpect(jsonPath("$[2].name", is("DominoPizza1")))
				.andExpect(jsonPath("$[2].email", is("domino.pizza@com.ua1")))
				.andExpect(jsonPath("$[2].additionalInfo", is("12346")))
				.andExpect(jsonPath("$[2].active", is(false)))
				.andExpect(jsonPath("$[2].locations.locations", is("k14 fl6; k14 fl7; k14 fl8")))
				.andExpect(jsonPath("$[2].locations.locationsId", contains(6, 7, 8)))
				.andDo(print());
		
		verify(vendorDtoService).findAll();
	}


	private List<VendorDto> getVendorDtoList() {
		List<VendorDto> vendorDtoList = new ArrayList<VendorDto>();

		VendorDto vendorDto1 = new VendorDto(1l, "DominoPizza", "domino.pizza@com.ua", "1234", true,
				new VendorDto.VendorLocations("k14 fl1; k14 fl2; k14 fl3", Arrays.asList(1l, 2l, 3l)));
		VendorDto vendorDto2 = new VendorDto(2l, "DominoPizza1", "domino.pizza@com.ua1", "12345", true,
				new VendorDto.VendorLocations("k14 fl3; k14 fl4; k14 fl5", Arrays.asList(3l, 4l, 5l)));
		VendorDto vendorDto3 = new VendorDto(3l, "DominoPizza1", "domino.pizza@com.ua1", "12346", false,
				new VendorDto.VendorLocations("k14 fl6; k14 fl7; k14 fl8", Arrays.asList(6l, 7l, 8l)));

		vendorDtoList.add(vendorDto1);
		vendorDtoList.add(vendorDto2);
		vendorDtoList.add(vendorDto3);
		
		return vendorDtoList;
		
	}

	private VendorDto getVendorDto() {

		VendorDto.VendorLocations vendorLocation = new VendorDto.VendorLocations("k14 fl1; k14 fl2; k14 fl3",
				Arrays.asList(1l, 2l, 3l));

		VendorDto vendorDto = new VendorDto(1l, "DominoPizza", "domino.pizza@com.ua", "1234", true, vendorLocation);

		return vendorDto;
	}

	private byte[] convertIntoJson(VendorDto e) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(e);
	}
}
