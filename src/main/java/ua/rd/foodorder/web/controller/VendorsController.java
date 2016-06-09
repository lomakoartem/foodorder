package ua.rd.foodorder.web.controller;

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

import ua.rd.foodorder.domain.Location;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.service.facade.VendorFacade;
import ua.rd.foodorder.web.controller.validators.VendorValidator;

/**
 * Created by Artem on 06.06.2016.
 */
@RestController
@RequestMapping(value = "/api/vendors")
public class VendorsController {

	private Logger logger = LoggerFactory.getLogger(VendorsController.class);

	private VendorFacade vendorFacade;

	private VendorValidator vendorValidator;

	@Autowired
	public VendorsController(VendorFacade vendorFacade) {
		this.vendorFacade = vendorFacade;
	}

	public VendorValidator getVendorValidator() {
		return vendorValidator;
	}

	@Autowired
	public void setVendorValidator(VendorValidator vendorValidator) {
		this.vendorValidator = vendorValidator;
	}

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.addValidators(vendorValidator);
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Vendor locationById(@PathVariable Long id) {
		return vendorFacade.findByIdAndCheck(id);
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void deleteLocationById(@PathVariable Long id) {
		vendorFacade.remove(id);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Iterable<Vendor> listVendor() {
		return vendorFacade.getVendorList();
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public Vendor editVendor(@PathVariable Long id, @Validated @RequestBody Vendor vendor,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new EntityFormatException();
		}

		return vendorFacade.editVendor(id, vendor);
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Vendor> addVendor(@RequestBody Vendor vendor, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {

		if (bindingResult.hasErrors()) {
			throw new EntityFormatException();
		}

		Vendor newVendor = vendorFacade.addVendor(vendor);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/vendors/list/{id}").buildAndExpand(newVendor.getId()).toUri());

		return new ResponseEntity<Vendor>(newVendor, headers, HttpStatus.CREATED);

	}

}
