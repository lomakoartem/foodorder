package ua.rd.foodorder.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.IEmployeeFileParser;
import ua.rd.foodorder.infrastructure.UserNameAndUpsaLinkTuple;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.infrastructure.exceptions.EntityWithTheSameLinkException;
import ua.rd.foodorder.infrastructure.exceptions.EntityWithTheSameNameException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.UserService;

@Service
@Transactional
public class SimpleUserService implements UserService {

	private static final String SPACE_STRING = " ";

	private static final String USER_NAME_WORDS_SEPARATOR = "_";

	private static final String EPAM_MAIL_ENDING = "@epam.com";
	
	private static final String SORT_BY_FIELD = "name";

	private UserRepository userRepository;

	private IEmployeeFileParser employeeFileParser;

	@Autowired
	public SimpleUserService(UserRepository userRepository, IEmployeeFileParser employeeFileParser) {
		this.userRepository = userRepository;
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

	@Override
	public void saveUsersFromFile(MultipartFile file) {
		List<UserNameAndUpsaLinkTuple> userNameAndUpsaLinkTupleList = employeeFileParser.parse(file);
		Set<User> usersFromTupleList = getUsersSetFromTupleList(userNameAndUpsaLinkTupleList);
		saveUsersWithoutDuplicates(usersFromTupleList);
	}

	private Set<User> getUsersSetFromTupleList(List<UserNameAndUpsaLinkTuple> userNameAndUpsaLinkTupleList) {
		Set<User> users = new HashSet<>();
		for (final UserNameAndUpsaLinkTuple tuple : userNameAndUpsaLinkTupleList) {
			User user = new User();
			String userName = tuple.getUserName();
			String upsaLink = tuple.getUpsaLink();
			String userEmail = generateEmailFromUserName(userName);
			user.setName(userName);
			user.setUpsaLink(upsaLink);
			user.setEmail(userEmail);
			users.add(user);
		}
		return users;
	}

	private String generateEmailFromUserName(String userName) {
		String userEmailWithoutMail = userName.trim().replaceAll(SPACE_STRING, USER_NAME_WORDS_SEPARATOR);
		String userEmail = new StringBuilder(userEmailWithoutMail).append(EPAM_MAIL_ENDING).toString();
		return userEmail;
	}

	private void saveUsersWithoutDuplicates(Set<User> uploadedUsers) {
		Set<User> dbUsersCopy = new HashSet<>();
		Set<User> dbUsers = new HashSet<>();
		StreamSupport.stream(userRepository.findAll().spliterator(), false).forEach(user -> {
			dbUsersCopy.add(user);
			dbUsers.add(user);
		});
		inactivateUsers(uploadedUsers, dbUsersCopy);
		saveNewUsers(uploadedUsers, dbUsers);
	}

	private void saveNewUsers(Set<User> uploadedUsers, Set<User> dbUsers) {
		uploadedUsers.removeAll(dbUsers);
		userRepository.save(uploadedUsers);
	}

	private void inactivateUsers(Set<User> uploadedUsers, Set<User> dbUsers) {
		dbUsers.removeAll(uploadedUsers);
		setUsersInactive(dbUsers);
		userRepository.save(dbUsers);
	}

	private void setUsersInactive(Set<User> oldUsers) {
		oldUsers.stream().forEach(user -> user.setActive(false));
	}

	@Override
	public Page<User> saveAndGetPage(User newUser, Integer size) {
		saveUser(newUser);
		Long count = userRepository.countNamesOfUsersThatLessNameOfNewUser(newUser.getName());
		Integer pageNumber =  getPageNumber(count, size);
		Page<User> page = getPageOfUsers(pageNumber - 1, size);
		return page;
	}
	
	private Integer getPageNumber(Long count, Integer size){
		if(count%size == 0){
			return (int) (count/size);
		}else{
			return (int) (count/size + 1);
		}
	}
	
	private void saveUser(User user){
		String userEmail = generateEmailFromUserName(user.getName());
		user.setEmail(userEmail);
		checkIfExistUserWithSuchNameOrUpsaLink(user.getName(), user.getUpsaLink());
		save(user);
	}
	
	private void checkIfExistUserWithSuchNameOrUpsaLink(String name, String upsaLink){
		List<User> users = userRepository.findByNameOrUpsaLink(name, upsaLink);
		for(User user: users){
			if(user.getName().equals(name)){
				throw new EntityWithTheSameNameException("There already exists such name.");
			}else if(user.getUpsaLink().equals(upsaLink)){
				throw new EntityWithTheSameLinkException("There already exists such UPSA link.");
			}
		}
	}
	
	private Page<User> getPageOfUsers(Integer pageNumber, Integer size){
		PageRequest pageRequest = new PageRequest(pageNumber, size, Sort.Direction.ASC, SORT_BY_FIELD);
		return getPageOfUsers(pageRequest);
	}

}
