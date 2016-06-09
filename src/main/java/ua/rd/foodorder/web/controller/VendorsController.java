package ua.rd.foodorder.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.rd.foodorder.domain.Vendor;
import ua.rd.foodorder.infrastructure.exceptions.ControllerError;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.service.VendorService;
import ua.rd.foodorder.web.controller.validators.VendorValidator;
import ua.rd.foodorder.web.dto.domain.VendorDto;
import ua.rd.foodorder.web.dto.service.VendorDtoService;

@RestController
@RequestMapping(value = "/api/vendors")
public class VendorsController {

    private Logger logger = LoggerFactory.getLogger(VendorsController.class);

    private VendorDtoService vendorDtoService;
    @Autowired
    public VendorsController(VendorDtoService vendorDtoService) {
        this.vendorDtoService = vendorDtoService;
    }

    @Autowired
    private VendorValidator vendorValidator;



    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(vendorValidator);
    }


    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public VendorDto locationById(@PathVariable Long id) {
        return vendorDtoService.findById(id);
    }


    @RequestMapping(value = "/list/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id) {
    	vendorDtoService.remove(id);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<VendorDto> listVendor() {
        return vendorDtoService.findAll();
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public VendorDto editVendor(@PathVariable Long id, @Validated @RequestBody VendorDto vendorDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }

        return vendorDtoService.update(vendorDto);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = "application/json")
    public VendorDto addVenderDto(@RequestBody VendorDto vendorDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }
            return vendorDtoService.save(vendorDto);
        }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ControllerError> locationNotFound(EntityNotFoundException e) {
        long locationId = e.getLocationId();
        ControllerError error = new ControllerError(1, "Location [" + locationId + "] not found");
        return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityFormatException.class)
    public ResponseEntity<ControllerError> locationIncorrectFormat(EntityFormatException e) {
        ControllerError error = new ControllerError(2, "Format of location object incorrect");
        return new ResponseEntity<ControllerError>(error, HttpStatus.NOT_ACCEPTABLE);
    }
}
