package ua.rd.foodorder.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

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
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable Long id){
        return userDTOService.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable Long id){
         userDTOService.remove(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<UserDTO> getAllUser() {
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
    public Page<UserDTO> getPageOfUsers(@PathVariable Integer pageNumber, @RequestParam("size") Integer size){
    	return userDTOService.getPageOfUserDTOs(pageNumber, size);
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upoloadEmployees(@RequestParam("file") MultipartFile mulitPartFile) {
		File file = new File(mulitPartFile.getOriginalFilename());
		try {
			mulitPartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
