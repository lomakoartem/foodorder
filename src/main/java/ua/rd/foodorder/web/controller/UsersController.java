package ua.rd.foodorder.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.rd.foodorder.infrastructure.exceptions.EntityFormatException;
import ua.rd.foodorder.web.controller.validators.UserDTOValidator;
import ua.rd.foodorder.web.dto.domain.UserDTO;
import ua.rd.foodorder.web.dto.service.UserDTOService;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/employees")
public class UsersController {

    private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);

    private UserDTOService userDTOService;

    private UserDTOValidator userDTOValidator;

    @RequestMapping(value = "/search/{searchTerm}", method = RequestMethod.GET)
    public Iterable<UserDTO> findUsersByFirstAndSecondName(@PathVariable String searchTerm,
                                                           @RequestParam("pageNumber") Integer pageNumber,
                                                           @RequestParam("size") Integer size) {

        return userDTOService.searchPageOfUserDTOs(searchTerm, pageNumber, size);
    }
    
    @RequestMapping(value = "/searchAll/{searchTerm}", method = RequestMethod.GET)
    public Iterable<UserDTO> findUsersByFirstAndSecondName(@PathVariable String searchTerm) {

        return userDTOService.searchAllOfUserDTOs(searchTerm);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id) {
        return userDTOService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id) {
        userDTOService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserDTO> getAllUser() {
        return userDTOService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public UserDTO editUser(@PathVariable Long id, @Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new EntityFormatException();
        }
        return userDTOService.update(userDTO);
    }   
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Page<UserDTO> saveAndGetPageWithSavedUser(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult, @RequestParam("size") Integer size){
    	if (bindingResult.hasErrors()) {
             throw new EntityFormatException();
         }
    	 Page<UserDTO> pageWithUser = userDTOService.saveAndGetPage(userDTO, size);
         return pageWithUser;
    }

    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    public Page<UserDTO> getPageOfUsers(@PathVariable Integer pageNumber, @RequestParam("size") Integer size) {
        return userDTOService.getPageOfUserDTOs(pageNumber, size);
    }
    
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void uploadEmployeesMultipartFile(@RequestParam("file") MultipartFile mulitPartFile) {
    	userDTOService.saveUsersFromFile(mulitPartFile);
	}

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.addValidators(userDTOValidator);
    }

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
}

