package ua.rd.foodorder.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.web.controller.validators.VendorDtoValidator;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.service.VendorDtoService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/vendors")
public class VendorsController {

	private Logger logger = LoggerFactory.getLogger(VendorsController.class);

	private VendorDtoService vendorDtoService;

	private VendorDtoValidator vendorDtoValidator;

	@Autowired
	public VendorsController(VendorDtoService vendorDtoService) {
		this.vendorDtoService = vendorDtoService;
	}

	public VendorDtoValidator getVendorValidator() {
		return vendorDtoValidator;
	}

	@Autowired
	public void setVendorValidator(VendorDtoValidator vendorDtoValidator) {
		this.vendorDtoValidator = vendorDtoValidator;
	}

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(vendorDtoValidator);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public VendorDto locationById(@PathVariable Long id) {
		return vendorDtoService.findById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteLocationById(@PathVariable Long id) {
		vendorDtoService.remove(id);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<VendorDto> listVendor() {
		return vendorDtoService.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public VendorDto editVendor(@PathVariable Long id, @Validated @RequestBody VendorDto vendorDto,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new EntityFormatException();
		}

		return vendorDtoService.update(vendorDto);

	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<VendorDto> addVendor(@RequestBody @Validated VendorDto vendorDto, BindingResult bindingResult,
			UriComponentsBuilder ucBuilder) {

		if (bindingResult.hasErrors()) {
			throw new EntityFormatException();
		}
		
		VendorDto newVendorDto = vendorDtoService.save(vendorDto);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/vendors/{id}").buildAndExpand(newVendorDto.getId()).toUri());

		return new ResponseEntity<VendorDto>(newVendorDto, headers, HttpStatus.CREATED);
	}

}
