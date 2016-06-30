package ua.rd.foodorder.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.GenericTuple;
import ua.rd.foodorder.infrastructure.ParserForExcelFile;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.UserService;

@Service
@Transactional
public class SimpleUserService implements UserService {

	private UserRepository userRepository;
	
	private ParserForExcelFile parserForExcelFile;
	
	@Autowired
	public void setParserForExcelFile(ParserForExcelFile parserForExcelFile){
		this.parserForExcelFile = parserForExcelFile;
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User update(User user) {
		User userInDB = userRepository.findOne(user.getId());

		if (userInDB == null) {
			throw new EntityNotFoundException(user.getId());
		}

		userInDB.setActive(user.isActive());
		userInDB.setAdmin(user.isAdmin());
		userInDB.setEmail(user.getEmail());
		userInDB.setName(user.getName());

		return userRepository.save(userInDB);
	}

	@Override
	public void remove(Long id) {
		User userInDB = userRepository.findOne(id);

		if (userInDB == null) {
			throw new EntityNotFoundException(id);
		}

		userInDB.setActive(false);

		userRepository.save(userInDB);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Page<User> getPageOfUsers(PageRequest pageRequest) {
		return userRepository.findAll(pageRequest);
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void saveUsersFromFile(MultipartFile file) {
		List<GenericTuple<String, String>> userNameAndUpsaLinkTupleList;
		try {
			userNameAndUpsaLinkTupleList = parserForExcelFile.parse(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<User> users = getUsersFromTupleList(userNameAndUpsaLinkTupleList);
		userRepository.save(users);
	}
	
	private List<User> getUsersFromTupleList(List<GenericTuple<String, String>> userNameAndUpsaLinkTuple) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
}
