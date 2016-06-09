package ua.rd.foodorder.web.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
		mockMvc.perform(get("/api/vendors/list/{id}", 1l)).andExpect(status().isOk()).andExpect(jsonPath("$id", is(1)))
				.andExpect(jsonPath("$name", is("DominoPizza")))
				.andExpect(jsonPath("$email", is("domino.pizza@com.ua"))).andExpect(jsonPath("$phone", is("1234")))
				.andExpect(jsonPath("$active", is(true)))
				.andExpect(jsonPath("$locations.locations", is("k14 fl1; k14 fl2; k14 fl3")))
				.andExpect(jsonPath("$locations.locationsId", contains(1, 2, 3)));
		verify(vendorDtoService).findById(1l);
		verifyNoMoreInteractions(vendorDtoService);
	}

	@Test
	public void findByIdVendorNotFoundShouldReturnHttpStatus404() throws Exception {
		when(vendorDtoService.findById(1l)).thenThrow(new EntityNotFoundException(1l));
		mockMvc.perform(get("/api/vendors/list/{id}", 1l)).andExpect(status().isNotFound());
		verify(vendorDtoService).findById(1l);
		verifyNoMoreInteractions(vendorDtoService);
	}

	private VendorDto getVendorDto() {

		VendorDto.VendorLocations vendorLocation = new VendorDto.VendorLocations("k14 fl1; k14 fl2; k14 fl3",
				Arrays.asList(1l, 2l, 3l));

		VendorDto vendorDto = new VendorDto(1l, "DominoPizza", "domino.pizza@com.ua", "1234", true, vendorLocation);

		return vendorDto;
	}
}
