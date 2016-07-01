package ua.rd.foodorder.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.IEmployeeFileParser;
import ua.rd.foodorder.infrastructure.UserNameAndUpsaLinkTuple;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.UserService;

@Service
@Transactional
public class SimpleUserService implements UserService {

	private static final String SPACE_REGEXP = "\\s";
	
	private static final String USER_NAME_WORDS_SEPARATOR = "_";
	
	private static final String EPAM_MAIL_ENDING = "@epam.com";
	
	private UserRepository userRepository;
	
	private IEmployeeFileParser employeeFileParser;
	
	@Autowired
	public void setEmployeeFileParser(IEmployeeFileParser employeeFileParser){
		this.employeeFileParser = employeeFileParser;
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
		List<UserNameAndUpsaLinkTuple> userNameAndUpsaLinkTupleList;
		userNameAndUpsaLinkTupleList = employeeFileParser.parse(file);
		Set<User> usersFromTupleList = getUsersSetFromTupleList(userNameAndUpsaLinkTupleList);
		userRepository.save(users);
	}
	
	private Set<User> getUsersSetFromTupleList(List<UserNameAndUpsaLinkTuple> userNameAndUpsaLinkTupleList) {
		Set<User> users = new HashSet<>();
		for (UserNameAndUpsaLinkTuple tuple : userNameAndUpsaLinkTupleList) {
			User user = new User();
			String userName = tuple.getUserName();
			String upsaLink = tuple.getUpsaLink();
			String userEmail = generateEmailFromUserName(userName);
			user.setName(userName);
			user.setHiperlink(upsaLink);
			user.setEmail(userEmail);
			users.add(user);
		}
		return users;
	}
	
	private String generateEmailFromUserName(String userName) {
		String[] userNameWords = userName.trim().split(SPACE_REGEXP);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < userNameWords.length; i++) {
			sb.append(userNameWords[i]);
			if (i != userNameWords.length - 1) {
				sb.append(USER_NAME_WORDS_SEPARATOR);
			}
		}
		sb.append(EPAM_MAIL_ENDING);
		String generatedEmail = sb.toString();
		return generatedEmail;
	}
	
}
