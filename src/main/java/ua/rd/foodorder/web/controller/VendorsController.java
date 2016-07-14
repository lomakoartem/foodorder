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

import ua.rd.foodorder.infrastructure.exceptions.ActionFailureException;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.web.controller.validators.VendorDTOValidator;
import ua.rd.foodorder.web.dto.domain.VendorDTO;
import ua.rd.foodorder.web.dto.service.VendorDTOService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/vendors")
public class VendorsController {

    private static final Logger LOG = LoggerFactory.getLogger(VendorsController.class);

    private VendorDTOService vendorDTOService;

    private VendorDTOValidator vendorDtoValidator;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorDTOService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
        vendorDTOService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<VendorDTO> getAllVendors() {
        return vendorDTOService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public VendorDTO editVendor(@PathVariable Long id, @Validated @RequestBody VendorDTO vendorDTO,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        return vendorDTOService.update(vendorDTO);
    }
    
    @RequestMapping(value = "/generatePassword/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void generatePasswordForVendor(@PathVariable Long id, @Validated @RequestBody VendorDTO vendorDTO,
                                BindingResult bindingResult) {
        
    	if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        if(!vendorDTOService.generateAndSendPassword(vendorDTO)){
        	throw new ActionFailureException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<VendorDTO> addVendor(@RequestBody @Validated VendorDTO vendorDTO, BindingResult bindingResult,
                                               UriComponentsBuilder ucBuilder) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        VendorDTO newVendorDTO = vendorDTOService.save(vendorDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/vendors/{id}").buildAndExpand(newVendorDTO.getId()).toUri());

        return new ResponseEntity<VendorDTO>(newVendorDTO, headers, HttpStatus.CREATED);
    }

    @Autowired
    public VendorsController(VendorDTOService vendorDTOService) {
        this.vendorDTOService = vendorDTOService;
    }

    public VendorDTOValidator getVendorValidator() {
        return vendorDtoValidator;
    }

    @Autowired
    public void setVendorValidator(VendorDTOValidator vendorDtoValidator) {
        this.vendorDtoValidator = vendorDtoValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(vendorDtoValidator);
    }
}
