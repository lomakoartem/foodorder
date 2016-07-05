package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.exceptions.SearchNotFoundException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.SearchUserService;

@Service
public class SimpleSearchUserService implements SearchUserService {

	private UserRepository userRepository;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Page<User> searchPageOfUsers(String searchTerm, PageRequest pageRequest) {
		String[] terms = searchTerm.split(" ");
		if(terms.length > 2){
			throw new SearchNotFoundException();
		}
		
		Page<User> users;

		if (terms.length == 1) {
			users = findByFirstNameOrLastName(pageRequest, terms[0]);
			return users;
		}
		else {
			users = findByFirstNameAndLastName(pageRequest, terms[0], terms[1]);
			return users;
		}
		

	}

	@Override
	public Iterable<User> searchUserByTerm(String searchTerm, Sort sort) {
		String[] terms = searchTerm.split(" ");
		if(terms.length > 2){
			throw new SearchNotFoundException();
		}
				
		if (terms.length == 1) {
			return findByFirstNameOrLastNameAll(terms[0], sort);
		}
		
		else {
			return findByFirstNameAndLastNameAll(terms[0], terms[1], sort);
		}
		
	}
	
	private Page<User> findByFirstNameAndLastName(PageRequest pageRequest, String firstNameTerm, String lastNameTerm) {
		return userRepository.findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(firstNameTerm, lastNameTerm,
				pageRequest);
	}

	private Page<User> findByFirstNameOrLastName(PageRequest pageRequest, String searchTerm) {
		return userRepository.findByNameContainingIgnoreCase(searchTerm, pageRequest);
	}

	private Iterable<User> findByFirstNameAndLastNameAll(String firstNameTerm, String lastNameTerm, Sort sort) {
		return userRepository.findByNameContainingIgnoreCaseAndNameContainingIgnoreCase(firstNameTerm, lastNameTerm, sort);
	}

	private Iterable<User> findByFirstNameOrLastNameAll(String searchTerm, Sort sort) {
		return userRepository.findByNameContainingIgnoreCase(searchTerm, sort);
	}

	

}
