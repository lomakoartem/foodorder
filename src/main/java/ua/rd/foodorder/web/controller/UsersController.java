package ua.rd.foodorder.web.controller;

import org.hibernate.boot.jaxb.spi.Binding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.service.UserService;
import ua.rd.foodorder.web.controller.validators.UserDTOValidator;
import ua.rd.foodorder.web.dto.domain.UserDTO;
import ua.rd.foodorder.web.dto.service.UserDTOService;

/**
 * Created by Iaroslav Grytsaienko on 17.06.2016.
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/api/employees")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    private UserDTOService userDTOService;

    private UserDTOValidator userDTOValidator;
    
    private UserService userService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO userById(@PathVariable Long id){
        return userDTOService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id){
         userDTOService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserDTO> listVendor() {
        return userDTOService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public UserDTO editUser(@PathVariable Long id, @Validated @RequestBody UserDTO userDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw  new EntityFormatException();
        }
        return userDTOService.update(userDTO);
    }

    public ResponseEntity<UserDTO> addUser(UserDTO userDTO, BindingResult bindingResult, UriComponentsBuilder componentsBuilder){
        if (bindingResult.hasErrors()){
            throw  new EntityFormatException();
        }
        UserDTO newUserDTO = userDTOService.save(userDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/api/users/{id}").buildAndExpand(newUserDTO.getId()).toUri());
        return new ResponseEntity<UserDTO>(newUserDTO, headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    public Page<User> getPageEmployee(@PathVariable Integer pageNumber, @RequestParam("size") Integer size){
    	Page<User> page = userService.getPageForUsers(pageNumber, size);
    	
    	int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
    	
    	return page;
    }


    @InitBinder
    private void initBinder(WebDataBinder binder){binder.addValidators(userDTOValidator);}

    public UserDTOService getUserDTOService() {
        return userDTOService;
    }

    @Autowired
    public void setUserDTOService(UserDTOService userDTOService) {
        this.userDTOService = userDTOService;
    }

    public UserDTOValidator getUserDTOValidator() {
        return userDTOValidator;
    }

    @Autowired
    public void setUserDTOValidator(UserDTOValidator userDTOValidator) {
        this.userDTOValidator = userDTOValidator;
    }
    
    @Autowired
    public void setUserService(UserService userService){
    	this.userService = userService;
    }
}
